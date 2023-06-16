import React, { useEffect, useState } from "react";
import "./unblockUser.css";
import axios from "axios";
import { toast } from "react-toastify";
import { useNavigate } from "react-router-dom";
import { useTheme } from "@emotion/react";
import {
  Box,
  Container,
  IconButton,
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableFooter,
  TableHead,
  TablePagination,
  TableRow,
} from "@mui/material";
import FirstPageIcon from "@mui/icons-material/FirstPage";
import KeyboardArrowLeft from "@mui/icons-material/KeyboardArrowLeft";
import KeyboardArrowRight from "@mui/icons-material/KeyboardArrowRight";
import KeyboardArrowUp from "@mui/icons-material/KeyboardArrowUp";
import KeyboardArrowDown from "@mui/icons-material/KeyboardArrowDown";
import LastPageIcon from "@mui/icons-material/LastPage";
import PropTypes from "prop-types";
import TemporaryDrawer from "../../common/navbar";

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

export default function UnblockUser() {
  const [page, setPage] = React.useState(0);
  const [rowsPerPage, setRowsPerPage] = React.useState(5);
  const [users, setUsers] = useState([]);
  const navigate = useNavigate();
  let [searchTerm, setSearchTerm] = useState("");

  const handleSearchInputChange = (event) => {
    setSearchTerm(event.target.value);
  };

  const jwtToken = localStorage.getItem("jwtToken");

  useEffect(() => {
    getUnblockedUsers();
  }, []);

  const axiosConfig = {
    headers: {
      Authorization: `Bearer ${jwtToken}`,
    },
  };

  const unblockUser = (email) => {
    axios
      .patch(`http://localhost:8085/admin/unblockUser/${email}`, axiosConfig)
      .then((response) => {
        console.log(response.data);
        toast.success("USER Unblocked");
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

  const getUnblockedUsers = () => {
    axios
      .get(`http://localhost:8085/admin/getDisabledUsers`, axiosConfig)
      .then((response) => {
        const result = response.data;
        console.log(result);
        setUsers(result);
      })
      .catch((error) => {
        console.log(error.message);
        if (error.message === "Request failed with status code 403") {
          navigate("/signIn");
          toast.warning("Logged in as User");
        }
      });
  };

  const searchUserByName = (searchTerm) => {
    // Convert the searchTerm to lowercase for case-insensitive search
    const upperSearchTerm = searchTerm.toUpperCase();

    // Filter the books array based on the matching book names
    const filteredUsers = users.filter((user) => {
      const upperUserName = user.name.toUpperCase();
      return upperUserName.includes(upperSearchTerm);
    });

    // Update the state with the filtered books
    console.log(filteredUsers);
    setUsers(filteredUsers);
  };

  const handleSearchButtonClick = () => {
    searchUserByName(searchTerm);
  };

  // Avoid a layout jump when reaching the last page with empty rows.
  const emptyRows =
    page > 0 ? Math.max(0, (1 + page) * rowsPerPage - users.length) : 0;

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
    <div>
      <TemporaryDrawer />
      <div className="main-body">
        <Container fluid sx={{ display: { xs: "none", md: "flex" }, ml: 12 }}>
          <TableContainer component={Paper} id="tableContainer">
            <div class="input-group">
              <input
                type="search"
                class="form-control rounded"
                placeholder="Search By User Name"
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
                onClick={getUnblockedUsers}
              >
                Reset
              </button>
            </div>
            <h2>All Blocked Users</h2>

            <Table
              sx={{ minWidth: 500 }}
              aria-label="custom pagination table"
              id="table"
            >
              <TableHead>
                <TableRow>
                  <TableCell padding="checkbox" />
                  <TableCell>Username</TableCell>
                  <TableCell align="center">Name</TableCell>
                  <TableCell align="center">Email</TableCell>
                  <TableCell align="center">Mobile No</TableCell>
                  <TableCell align="center">Status</TableCell>
                  <TableCell align="center">Unblock</TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {(rowsPerPage > 0
                  ? users.slice(
                      page * rowsPerPage,
                      page * rowsPerPage + rowsPerPage
                    )
                  : users
                ).map((row) => (
                  <TableRow key={row.userName} id="list">
                    <TableCell padding="checkbox" />
                    <TableCell component="th" scope="row">
                      {row.userName}
                    </TableCell>
                    <TableCell align="center">{row.name}</TableCell>
                    <TableCell align="center">
                      {row.email}
                    </TableCell>
                    <TableCell align="center">
                    {row.mobileNo}
                    </TableCell>
                    <TableCell align="center">{row.status}</TableCell>
                    <TableCell align="center">
                      <button
                        type="button"
                        className="btn btn-warning"
                        onClick={() => unblockUser(row.email)}
                      >
                        Unblock
                      </button>
                    </TableCell>
                  </TableRow>
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
                    rowsPerPageOptions={[
                      5,
                      10,
                      25,
                      { label: "All", value: -1 },
                    ]}
                    colSpan={11}
                    count={users.length}
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
        </Container>
      </div>
    </div>
  );
}
