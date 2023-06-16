import React, { Children, cloneElement, useEffect, useState } from "react";
import "./allAuthors.css";
import { useTheme } from "@emotion/react";
import {
  Box,
  Container,
  IconButton,
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
} from "@mui/material";
import FirstPageIcon from "@mui/icons-material/FirstPage";
import KeyboardArrowLeft from "@mui/icons-material/KeyboardArrowLeft";
import KeyboardArrowRight from "@mui/icons-material/KeyboardArrowRight";
import KeyboardArrowUp from "@mui/icons-material/KeyboardArrowUp";
import KeyboardArrowDown from "@mui/icons-material/KeyboardArrowDown";
import LastPageIcon from "@mui/icons-material/LastPage";
import PropTypes from "prop-types";
import { useNavigate } from "react-router-dom";
import TemporaryDrawer from "../../common/navbar";
import { toast } from "react-toastify";
import axios from "axios";

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

export default function AllAuthors() {
  const [page, setPage] = useState(0);
  const [rowsPerPage, setRowsPerPage] = useState(5);
  const navigate = useNavigate();
  let [authors, setAuthors] = useState([]);
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

  useEffect(() => {
    getAllAuthors();
  }, []);

  const getAllAuthors = () => {
    axios
      .get(`http://localhost:8085/author/getAllAuthors`, axiosConfig)
      .then((response) => {
        const result = response.data;
        setAuthors(result);
        console.log(result);
      })
      .catch((error) => {
        console.log(error.response.data);
        toast.warning(error.response);
      });
  };

  const getBooksByAuthor = (name) => {
    return axios
      .get(
        `http://localhost:8085/book/getBookByAuthorName/${name}`,
        axiosConfig
      )
      .then((response) => {
        console.log(response.data);
      })
      .catch((error) => {
        console.error(error.message);
        throw error;
      });
  };

  const deleteAuthor = (id) => {
    console.log(id);
    const confirmDelete = window.confirm(
      "Are you sure you want to delete this Author?"
    );

    if (confirmDelete) {
      axios
        .delete(`http://localhost:8085/author/deleteAuthor/${id}`, axiosConfig)
        .then((response) => {
          toast.error("Author Deleted!!!");
          getAllAuthors();
        })
        .catch((err) => {
          console.log(err.response.data);
          toast.warning(err.response.data);
        });
    }
  };

  const searchAuthorByName = (searchTerm) => {
    // Convert the searchTerm to lowercase for case-insensitive search
    const upperSearchTerm = searchTerm.toUpperCase();

    // Filter the books array based on the matching book names
    const filteredAuthors = authors.filter((author) => {
      const upperAuthorName = author.authorName.toUpperCase();
      return upperAuthorName.includes(upperSearchTerm);
    });

    // Update the state with the filtered books
    console.log(filteredAuthors);
    setAuthors(filteredAuthors);
  };

  const handleSearchButtonClick = () => {
    searchAuthorByName(searchTerm);
  };

  // Avoid a layout jump when reaching the last page with empty rows.
  const emptyRows =
    page > 0 ? Math.max(0, (1 + page) * rowsPerPage - authors.length) : 0;

  const handleChangePage = (event, newPage) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = (event) => {
    setRowsPerPage(parseInt(event.target.value, 10));
    setPage(0);
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
                placeholder="Search By Author Name"
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
                onClick={getAllAuthors}
              >
                Reset
              </button>
            </div>
            <h2>All Authors</h2>

            <Table
              sx={{ minWidth: 500 }}
              aria-label="custom pagination table"
              id="table"
            >
              <TableHead>
                <TableRow>
                  <TableCell padding="checkbox" />
                  <TableCell>Author Id</TableCell>
                  <TableCell align="center">Author Name</TableCell>
                  <TableCell align="center">Rating</TableCell>
                  <TableCell align="center">Delete</TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {(rowsPerPage > 0
                  ? authors.slice(
                      page * rowsPerPage,
                      page * rowsPerPage + rowsPerPage
                    )
                  : authors
                ).map((row) => (
                  <TableRow
                    key={row.authorId}
                    
                  >
                    <TableCell padding="checkbox" />
                    <TableCell component="th" scope="row">
                      {row.authorId}
                    </TableCell>
                    <TableCell align="center">{row.authorName}</TableCell>
                    <TableCell align="center">
                      <Rating name="readOnly" value={row.rating} readOnly />
                    </TableCell>
                    <TableCell align="center">
                      <button
                        type="button"
                        className="btn btn-danger"
                        onClick={() => deleteAuthor(row.authorId)}
                      >
                        Delete
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
                    count={authors.length}
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
