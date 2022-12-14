import {
  Container,
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
} from "@mui/material";
import axios from "axios";
import React, { useEffect, useState } from "react";
import Rating from "@mui/material/Rating";
import Typography from "@mui/material/Typography";
import { toast } from "react-toastify";

export const AllBooks = () => {
  let [Books, setBooks] = useState([]);
  let [errorMsg, setErrorMsg] = useState("");

  useEffect(() => {
    getAllBooks();
  }, []);

  const getAllBooks = () => {
    axios
      .get("http://localhost:8093/admin/getRating")
      .then((response) => {
        const result = response.data;
        console.log(result);
        if (result === null) throw errorMsg;
        setBooks(result);
      })
      .catch((error) => {
        setErrorMsg("error ");
        toast.warning(error.response.data);
        console.log(error.response.data);
      });
  };

  return (
    <div>
      <div className="container-fluid">
        <Container fluid sx={{ display: { xs: "none", md: "flex" }, ml: 12 }}>
          <TableContainer component={Paper}>
            <h2>All Ratings</h2>
            <Table
              sx={{ minWidth: 650 }}
              aria-label="simple table"
              className="Table"
            >
              <TableHead className="TableHead">
                <TableRow>
                  <TableCell>Book ID</TableCell>
                  <TableCell align="center">Stars</TableCell>
                  <TableCell align="center">Title</TableCell>
                  <TableCell align="center">Description</TableCell>
                </TableRow>
              </TableHead>
              <TableBody className="TableBody">
                {Books.map((book) => (
                  <TableRow
                    key={book.bookId}
                    sx={{ "&:last-child td, &:last-child th": { border: 0 } }}
                  >
                    <TableCell component="th" scope="row">
                      <Typography component="legend">{book.bookId}</Typography>
                    </TableCell>
                    <TableCell align="center">
                      <Typography component="legend">{book.author.authorName}</Typography>
                    </TableCell>
                    <TableCell align="center">
                      <Typography component="legend">{book.inStock}</Typography>
                    </TableCell>
                    <TableCell align="center">
                      <Typography component="legend">{book.copiesSold}</Typography>
                    </TableCell>
                    <TableCell align="center">
                      <Typography component="legend">{book.price}</Typography>
                    </TableCell>
                    <TableCell align="center">
                      <Typography component="legend">
                        {book.description}
                      </Typography>
                    </TableCell>
                    <TableCell align="center">
                      <Rating name="read-only" value={book.rating} readOnly />
                    </TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          </TableContainer>
        </Container>
      </div>
    </div>
  );
};
