import { Container } from "@mui/material";
import axios from "axios";
import React, { useState } from "react";
import { useNavigate } from "react-router";
import { toast } from "react-toastify";
import TemporaryDrawer from "../../common/navbar";
import "./AddBook.css";

export const AddBook = () => {
  const [bookName, setBookName] = useState("");
  const [description, setDescription] = useState("");
  const [inStock, setInStock] = useState("");
  const [price, setPrice] = useState("");
  const [author, setAuthor] = useState({ authorName: "" });
  const navigate = useNavigate();

  React.useEffect(() => {
    console.log(jwtToken);
    if (jwtToken === null) {
      toast.warning("Please Login First");
      navigate("/signin");
    }
    console.log(jwtToken);
  }, []);

  const jwtToken = localStorage.getItem("jwtToken");
  const axiosConfig = {
    headers: {
      Authorization: `Bearer ${jwtToken}`,
    },
  };

  const handleAuthorNameChange = (e) => {
    setAuthor({ ...author, authorName: e.target.value });
  };
  const handleSubmit = (e) => {
    e.preventDefault();

    const data = {
      bookName,
      author,
      description,
      inStock,
      price,
    };

    console.log(data);

    axios
      .post("http://localhost:8085/book/addBook", data, axiosConfig)
      .then((resp) => {
        console.log(resp.data);
        toast.success("Book Added Successfuly");
      })
      .catch((err) => {
        console.log(err);
        toast.error(err.response.data);
      });
  };

  return (
    <div className="body">
      <TemporaryDrawer />
      <div className="main-body">
        <Container fluid sx={{ display: { xs: "none", md: "flex" }, ml: 12 }}>
          <section className="get-in-touch">
            <h1 className="title">Add Book</h1>
            <form className="contact-form row">
              <div className="form-field col-lg-6">
                <input
                  name="BookName"
                  className="input-text js-input"
                  type="text"
                  onChange={(e) => setBookName(e.target.value)}
                  required
                />
                <label className="label" for="name">
                  Book Name
                </label>
              </div>
              <div className="form-field col-lg-6">
                <input
                  name="AuthorName"
                  className="input-text js-input"
                  type="text"
                  onChange={handleAuthorNameChange}
                  required
                />
                <label className="label" for="name">
                  Author Name
                </label>
              </div>
              <div className="form-field col-lg-12">
                <input
                  name="Description"
                  className="input-text js-input"
                  type="text"
                  onChange={(e) => setDescription(e.target.value)}
                  required
                />
                <label className="label" for="name">
                  Book Description
                </label>
              </div>
              <div className="form-field col-lg-6 ">
                <input
                  className="input-text js-input"
                  type="number"
                  name="InStock"
                  onChange={(e) => setInStock(e.target.value)}
                  required
                />
                <label className="label" for="company">
                  Books In Stock
                </label>
              </div>
              <div className="form-field col-lg-6 ">
                <input
                  className="input-text js-input"
                  type="number"
                  name="Price"
                  onChange={(e) => setPrice(e.target.value)}
                  required
                />
                <label className="label" for="company">
                  Books Price
                </label>
              </div>
              <div className="form-field col-lg-12">
                <input
                  className="submit-btn"
                  type="submit"
                  value="Submit"
                  onClick={handleSubmit}
                />
                &nbsp;&nbsp;&nbsp;&nbsp;
                <input className="submit-btn" type="reset" value="Reset" />
              </div>
            </form>
          </section>
        </Container>
      </div>
    </div>
  );
};
