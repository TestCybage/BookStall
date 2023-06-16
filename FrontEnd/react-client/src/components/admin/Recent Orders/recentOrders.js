import * as React from "react";
import PropTypes from "prop-types";
import { useTheme } from "@mui/material/styles";
import Box from "@mui/material/Box";
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableFooter from "@mui/material/TableFooter";
import TablePagination from "@mui/material/TablePagination";
import TableRow from "@mui/material/TableRow";
import Paper from "@mui/material/Paper";
import IconButton from "@mui/material/IconButton";
import FirstPageIcon from "@mui/icons-material/FirstPage";
import KeyboardArrowLeft from "@mui/icons-material/KeyboardArrowLeft";
import KeyboardArrowRight from "@mui/icons-material/KeyboardArrowRight";
import LastPageIcon from "@mui/icons-material/LastPage";
import "./recentOrders.css";
import axios from "axios";
import { toast } from "react-toastify";
import { useNavigate } from "react-router-dom";
import KeyboardArrowUp from "@mui/icons-material/KeyboardArrowUp";
import KeyboardArrowDown from "@mui/icons-material/KeyboardArrowDown";
import { TableHead } from "@mui/material";

function TablePaginationActions(props) {
  const theme = useTheme();
  const { count, page, rowsPerPage, onPageChange } = props;

  const handleFirstPageButtonClick = (event) => {
    onPageChange(event, 0);
  };

  const handleBackButtonClick = (event) => {
    onPageChange(event, page - 1);
  };

  const handleNextButtonClick = (event) => {
    onPageChange(event, page + 1);
  };

  const handleLastPageButtonClick = (event) => {
    onPageChange(event, Math.max(0, Math.ceil(count / rowsPerPage) - 1));
  };

  return (
    <Box sx={{ flexShrink: 0, ml: 2.5 }}>
      <IconButton
        onClick={handleFirstPageButtonClick}
        disabled={page === 0}
        aria-label="first page"
      >
        {theme.direction === "rtl" ? <LastPageIcon /> : <FirstPageIcon />}
      </IconButton>
      <IconButton
        onClick={handleBackButtonClick}
        disabled={page === 0}
        aria-label="previous page"
      >
        {theme.direction === "rtl" ? (
          <KeyboardArrowRight />
        ) : (
          <KeyboardArrowLeft />
        )}
      </IconButton>
      <IconButton
        onClick={handleNextButtonClick}
        disabled={page >= Math.ceil(count / rowsPerPage) - 1}
        aria-label="next page"
      >
        {theme.direction === "rtl" ? (
          <KeyboardArrowLeft />
        ) : (
          <KeyboardArrowRight />
        )}
      </IconButton>
      <IconButton
        onClick={handleLastPageButtonClick}
        disabled={page >= Math.ceil(count / rowsPerPage) - 1}
        aria-label="last page"
      >
        {theme.direction === "rtl" ? <FirstPageIcon /> : <LastPageIcon />}
      </IconButton>
    </Box>
  );
}

TablePaginationActions.propTypes = {
  count: PropTypes.number.isRequired,
  onPageChange: PropTypes.func.isRequired,
  page: PropTypes.number.isRequired,
  rowsPerPage: PropTypes.number.isRequired,
};

export default function CustomPaginationActionsTable() {
  const [page, setPage] = React.useState(0);
  const [rowsPerPage, setRowsPerPage] = React.useState(5);
  const [rows, setRows] = React.useState([]);
  const navigate = useNavigate();
  let [searchTerm, setSearchTerm] = React.useState("");

  const handleSearchInputChange = (event) => {
    setSearchTerm(event.target.value);
  };

  const jwtToken = localStorage.getItem("jwtToken");
  const axiosConfig = {
    headers: {
      Authorization: `Bearer ${jwtToken}`,
    },
  };
  React.useEffect(() => {
    getAllOrders();
  }, []);

  const getAllOrders = () => {
    axios
      .get("http://localhost:8085/order/getAllOrders", axiosConfig)
      .then((response) => {
        const result = response.data;
        setRows(result);
        console.log(result);
      })
      .catch((error) => {
        console.error(error.message);
        if (error.message === "Request failed with status code 403") {
          navigate("/signIn");
          toast.warning("Logged in as User");
        }
        console.log(error.response.data);
        toast.warning(error.response);
      });
  };

  const searchOrderById = (searchTerm) => {
    // Filter the orders array based on the matching order Id
    const filteredOrders = rows.filter((row) => {
      const orderId = row.orderId.toString();
      return orderId.includes(searchTerm);
    });

    const sortedOrders = filteredOrders.sort((a, b) => a.orderId - b.orderId);
    // Update the state with the filtered books
    console.log(sortedOrders);
    setRows(sortedOrders);
  };

  const handleSearchButtonClick = () => {
    searchOrderById(searchTerm);
  };
  // Avoid a layout jump when reaching the last page with empty rows.
  const emptyRows =
    page > 0 ? Math.max(0, (1 + page) * rowsPerPage - rows.length) : 0;

  const handleChangePage = (event, newPage) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = (event) => {
    setRowsPerPage(parseInt(event.target.value, 10));
    setPage(0);
  };

  const ExpandableTableRow = ({ children, expandComponent, ...otherProps }) => {
    const [isExpanded, setIsExpanded] = React.useState(false);
    return (
      <>
        <TableRow {...otherProps}>
          <TableCell padding="checkbox">
            <IconButton onClick={() => setIsExpanded(!isExpanded)}>
              {isExpanded ? <KeyboardArrowUp /> : <KeyboardArrowDown />}
            </IconButton>
          </TableCell>
          {children}
        </TableRow>
        {isExpanded && (
          <TableRow>
            <TableCell padding="checkbox" />
            {expandComponent}
          </TableRow>
        )}
      </>
    );
  };

  return (
    <TableContainer component={Paper} id="tableContainer">
      <div class="input-group">
        <input
          type="search"
          class="form-control rounded"
          placeholder="Search By Order Id"
          aria-label="Search"
          aria-describedby="search-addon"
          value={searchTerm}
          onChange={handleSearchInputChange}
        />
        <button
          type="submit"
          class="btn btn-outline-primary"
          onClick={handleSearchButtonClick}
        >
          search
        </button>
        <button
          type="Reset"
          class="btn btn-outline-dark"
          onClick={getAllOrders}
        >
          Reset
        </button>
      </div>
      <h2>Recent Orders</h2>
      <Table
        sx={{ minWidth: 500 }}
        aria-label="custom pagination table"
        id="table"
      >
        <TableHead>
          <TableRow>
            <TableCell padding="checkbox" />
            <TableCell>Order Id</TableCell>
            <TableCell align="center">Customer Name</TableCell>
            <TableCell align="center">Amount</TableCell>
            <TableCell align="center">City</TableCell>
            <TableCell align="center">Status</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {(rowsPerPage > 0
            ? rows.slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
            : rows
          ).map((row) => (
            <ExpandableTableRow
              key={row.orderId}
              id="list"
              expandComponent={
                <TableRow>
                  <TableCell colSpan={6}>
                    <Table>
                      <TableHead>
                        <TableRow>
                          <TableCell>Book Name</TableCell>
                          <TableCell>Quantity</TableCell>
                        </TableRow>
                      </TableHead>
                      <TableBody>
                        {Object.entries(row.books).map(
                          ([bookName, quantity]) => (
                            <TableRow key={bookName}>
                              <TableCell>{bookName}</TableCell>
                              <TableCell>{quantity}</TableCell>
                            </TableRow>
                          )
                        )}
                      </TableBody>
                    </Table>
                  </TableCell>
                </TableRow>
              }
            >
              <TableCell component="th" scope="row">
                {row.orderId}
              </TableCell>
              <TableCell align="center">{row.user.name}</TableCell>
              <TableCell align="center">{row.amount}</TableCell>
              <TableCell align="center">{row.address.city}</TableCell>
              <TableCell align="center">{row.status}</TableCell>
            </ExpandableTableRow>
          ))}

          {emptyRows > 0 && (
            <TableRow style={{ height: 53 * emptyRows }}>
              <TableCell colSpan={6} />
            </TableRow>
          )}
        </TableBody>
        <TableFooter id="tableFooter">
          <TableRow>
            <TablePagination
              rowsPerPageOptions={[5, 10, 25, { label: "All", value: -1 }]}
              colSpan={6}
              count={rows.length}
              rowsPerPage={rowsPerPage}
              page={page}
              SelectProps={{
                inputProps: {
                  "aria-label": "rows per page",
                },
                native: true,
              }}
              onPageChange={handleChangePage}
              onRowsPerPageChange={handleChangeRowsPerPage}
              ActionsComponent={TablePaginationActions}
            />
          </TableRow>
        </TableFooter>
      </Table>
    </TableContainer>
  );
}
