import React, { useEffect, useState } from "react";
import "./addAuthor.css";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";
import TemporaryDrawer from "../../common/navbar";
import { Container } from "@mui/material";
import axios from "axios";

export const AddAuthor = () => {
  const navigate = useNavigate();
  const [authorName, setAuthorName] = useState("");
  const jwtToken = localStorage.getItem("jwtToken");

  useEffect(() => {
    console.log(jwtToken);
    if (jwtToken === null) {
      toast.warning("Please Login First");
      navigate("/signin");
    }
    console.log(jwtToken);
  }, []);

  const axiosConfig = {
    headers: {
      Authorization: `Bearer ${jwtToken}`,
    },
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    const data = {
      authorName,
    };

    console.log(data);

    axios
      .post("http://localhost:8085/author/addAuthor", data, axiosConfig)
      .then((resp) => {
        console.log(resp.data);
        toast.success("Author Added Successfuly");
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
            <h1 className="title">Add Author</h1>
            <form className="contact-form row">
              <div className="form-field col-lg-4">
                <input
                  name="BookName"
                  className="input-text js-input"
                  type="text"
                  onChange={(e) => setAuthorName(e.target.value)}
                  required
                />
                <label className="label" for="name">
                  Author Name
                </label>
              </div>
              <div className="form-field col-lg-4">
                <input
                  className="submit-btn"
                  type="submit"
                  value="Submit"
                  onClick={handleSubmit}
                />
              </div>
              <div className="form-field col-lg-4">
                <input className="submit-btn" type="reset" value="Reset" />
              </div>
            </form>
          </section>
        </Container>
      </div>
    </div>
  );
};
