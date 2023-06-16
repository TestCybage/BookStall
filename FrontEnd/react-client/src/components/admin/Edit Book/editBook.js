import React, { useState } from "react";
import "./editBook.css";
import TemporaryDrawer from "../../common/navbar";
import { Container } from "@mui/material";
import axios from "axios";
import { toast } from "react-toastify";
import { useNavigate } from "react-router-dom";

export default function EditBooks() {
  const [price, setPrice] = useState("");
  const [quantity, setQuantity] = useState("");
  const bookId = localStorage.getItem("bookId");
  const navigate = useNavigate();

  const jwtToken = localStorage.getItem("jwtToken");
  const axiosConfig = {
    headers: {
      Authorization: `Bearer ${jwtToken}`,
    },
  };

  const editPrice = () =>{
    axios.patch(`http://localhost:8085/book/editPrice/${bookId}/${price}`,axiosConfig)
    .then((response)=>{
        console.log(response.data);
        toast.success("Price updated");
    })
    .catch((error)=>{
        console.error(error.message);
        if (error.message === "Request failed with status code 403") {
          navigate("/signIn");
          toast.warning("Logged in as User");
        }
        console.log(error.response.data);
        toast.warning(error.response);
    })
  }

  const editQuantity = () =>{
    axios.patch(`http://localhost:8085/book/editQuantity/${bookId}/${quantity}`,axiosConfig)
    .then((response)=>{
        console.log(response.data);
        toast.success("Quantity updated");
    })
    .catch((error)=>{
        console.error(error.message);
        if (error.message === "Request failed with status code 403") {
          navigate("/signIn");
          toast.warning("Logged in as User");
        }
        console.log(error.response.data);
        toast.warning(error.response);
    })
  }

  return (
    <div className="body">
      <TemporaryDrawer />
      <div className="main-body">
        <Container fluid sx={{ display: { xs: "none", md: "flex" }, ml: 12 }}>
          <section className="get-in-touch">
            <h1 className="title">Change Book Price</h1>
            <form className="contact-form row">
              <div className="form-field col-lg-4">
                <input
                  name="BookPrice"
                  className="input-text js-input"
                  type="number"
                     onChange={(e) => setPrice(e.target.value)}
                  required
                />
                <label className="label" for="name">
                  Enter New Price
                </label>
              </div>
              <div className="form-field col-lg-4">
                <input
                  className="submit-btn"
                  type="submit"
                  value="Submit"
                     onClick={editPrice}
                />
              </div>
              <div className="form-field col-lg-4">
                <input className="submit-btn" type="reset" value="Reset" />
              </div>
            </form>
            <h1 className="title">Change Book Quantity</h1>
            <form className="contact-form row">
              <div className="form-field col-lg-4">
                <input
                  name="BookQuantity"
                  className="input-text js-input"
                  type="number"
                     onChange={(e) => setQuantity(e.target.value)}
                  required
                />
                <label className="label" for="name">
                  Enter New Quantity
                </label>
              </div>
              <div className="form-field col-lg-4">
                <input
                  className="submit-btn"
                  type="submit"
                  value="Submit"
                     onClick={editQuantity}
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
}
