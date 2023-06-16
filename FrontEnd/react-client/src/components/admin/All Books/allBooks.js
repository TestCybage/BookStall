import axios, { Axios } from "axios";
import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";
import TemporaryDrawer from "../../common/navbar";
import {
  Box,
  Container,
  IconButton,
  InputAdornment,
  Paper,
  Rating,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableFooter,
  TableHead,
  TablePagination,
  TableRow,
  TextField,
} from "@mui/material";
import "./allBooks.css";
import { useTheme } from "@emotion/react";
import FirstPageIcon from "@mui/icons-material/FirstPage";
import KeyboardArrowLeft from "@mui/icons-material/KeyboardArrowLeft";
import KeyboardArrowRight from "@mui/icons-material/KeyboardArrowRight";
import KeyboardArrowUp from "@mui/icons-material/KeyboardArrowUp";
import KeyboardArrowDown from "@mui/icons-material/KeyboardArrowDown";
import LastPageIcon from "@mui/icons-material/LastPage";
import PropTypes from "prop-types";

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

export default function AllBooks() {
  const [page, setPage] = React.useState(0);
  const [rowsPerPage, setRowsPerPage] = React.useState(5);
  const navigate = useNavigate();
  let [books, setBooks] = useState([]);
  let [searchTerm, setSearchTerm] = useState("");

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
    getAllBooks();
  }, []);

  const getAllBooks = () => {
    axios
      .get(`http://localhost:8085/book/getAllBooks`, axiosConfig)
      .then((response) => {
        const result = response.data;
        setBooks(result);
        console.log(result);
      })
      .catch((error) => {
        console.log(error.response.data);
        toast.warning(error.response);
      });
  };

  const deleteBook = (id) => {
    console.log(id);
    const confirmDelete = window.confirm(
      "Are you sure you want to delete this book?"
    );

    if (confirmDelete) {
      axios
        .delete(`http://localhost:8085/book/deleteBook/${id}`, axiosConfig)
        .then((response) => {
          toast.error("Book Deleted!!!");
          getAllBooks();
        })
        .catch((err) => {
          console.log(err.response.data);
          toast.warning(err.response.data);
        });
    }
  };
  const editBook = (id) => {
    console.log(id);
    localStorage.setItem("bookId", id);
    navigate("/editBook");
  };

  const searchBookByName = (searchTerm) => {
    // Convert the searchTerm to lowercase for case-insensitive search
    const upperSearchTerm = searchTerm.toUpperCase();

    // Filter the books array based on the matching book names
    const filteredBooks = books.filter((book) => {
      const upperBookName = book.bookName.toUpperCase();
      return upperBookName.includes(upperSearchTerm);
    });

    // Update the state with the filtered books
    console.log(filteredBooks);
    setBooks(filteredBooks);
  };

  const handleSearchButtonClick = () => {
    searchBookByName(searchTerm);
  };

  // Avoid a layout jump when reaching the last page with empty rows.
  const emptyRows =
    page > 0 ? Math.max(0, (1 + page) * rowsPerPage - books.length) : 0;

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
                placeholder="Search By Book Name"
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
                onClick={getAllBooks}
              >
                Reset
              </button>
            </div>
            <h2>All Books</h2>

            <Table
              sx={{ minWidth: 500 }}
              aria-label="custom pagination table"
              id="table"
            >
              <TableHead>
                <TableRow>
                  <TableCell padding="checkbox" />
                  <TableCell>Book ID</TableCell>
                  <TableCell align="center">Book Name</TableCell>
                  <TableCell align="center">Author Name</TableCell>
                  <TableCell align="center">Author Rating</TableCell>
                  <TableCell align="center">Books In Stock</TableCell>
                  <TableCell align="center">Copies Sold</TableCell>
                  <TableCell align="center">Price</TableCell>
                  <TableCell align="center">Book Rating</TableCell>
                  <TableCell align="center">Update</TableCell>
                  <TableCell align="center">Delete</TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {(rowsPerPage > 0
                  ? books.slice(
                      page * rowsPerPage,
                      page * rowsPerPage + rowsPerPage
                    )
                  : books
                ).map((row) => (
                  <ExpandableTableRow
                    key={row.bookId}
                    id="list"
                    expandComponent={
                      <TableRow>
                        <TableCell colSpan="5" id="Description">
                          Description
                        </TableCell>
                        <TableCell colSpan="5">{row.description}</TableCell>
                      </TableRow>
                    }
                  >
                    <TableCell component="th" scope="row">
                      {row.bookId}
                    </TableCell>
                    <TableCell align="center">{row.bookName}</TableCell>
                    <TableCell align="center">
                      {row.author.authorName}
                    </TableCell>
                    <TableCell align="center">
                      <Rating
                        name="readOnly"
                        value={row.author.rating}
                        readOnly
                      />
                    </TableCell>
                    <TableCell align="center">{row.inStock}</TableCell>
                    <TableCell align="center">{row.copiesSold}</TableCell>
                    <TableCell align="center">{row.price}</TableCell>
                    <TableCell align="center">
                      <Rating name="readOnly" value={row.rating} readOnly />
                    </TableCell>
                    <TableCell align="center">
                      <button
                        type="button"
                        className="btn btn-warning"
                        onClick={() => editBook(row.bookId)}
                      >
                        Edit
                      </button>
                    </TableCell>
                    <TableCell align="center">
                      <button
                        type="button"
                        className="btn btn-danger"
                        onClick={() => deleteBook(row.bookId)}
                      >
                        Delete
                      </button>
                    </TableCell>
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
                    rowsPerPageOptions={[
                      5,
                      10,
                      25,
                      { label: "All", value: -1 },
                    ]}
                    colSpan={11}
                    count={books.length}
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
