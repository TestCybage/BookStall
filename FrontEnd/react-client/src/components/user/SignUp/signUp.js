import * as React from "react";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";
import axios from "axios";
import "./signUp.css";

export default function SignUp() {
  const [userName, setUserName] = useState("");
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [mobileNo, setMobileNo] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();

  const signUpUser = (e) => {
    e.preventDefault();

    if (userName.length === 0) {
      toast.warning("Please Enter UserName");
    } else if (name.length === 0) {
      toast.warning("Please Enter Your Name");
    } else if (email.length === 0) {
      toast.warning("Please Enter Email");
    } else if (password.length < 8) {
      toast.warning("Password should be at least 8 letters");
    } else if (mobileNo.length !== 10) {
      toast.warning("Please Enter valid 10 digit Mobile Number");
    } else {
      const body = {
        userName,
        name,
        email,
        password,
        mobileNo,
      };

      const url = `http://localhost:8085/signUp`;

      axios
        .post(url, body)
        .then((response) => {
          const result = response.data;
          console.log(result);
          toast.success("Signed Up successfuly as new User");
          navigate("/signin");
        })
        .catch((error) => {
          console.error(error.response.data);
          toast.error(error.response.data);
        });
    }
  };

  return (
    <div className="login-box">
      <h2>SignUp</h2>
      <form>
        <div className="user-box">
          <input
            type="text"
            name=""
            required=""
            placeholder="Enter unique Username"
            onChange={(e) => {
              setUserName(e.target.value);
            }}
          />
          <label>UserName</label>
        </div>
        <div className="user-box">
          <input
            type="text"
            name=""
            required=""
            placeholder="Enter your name"
            onChange={(e) => {
              setName(e.target.value);
            }}
          />
          <label>Name</label>
        </div>
        <div className="user-box">
          <input
            type="email"
            name=""
            required=""
            placeholder="Enter Email"
            onChange={(e) => {
              setEmail(e.target.value);
            }}
          />
          <label>Email</label>
        </div>
        <div className="user-box">
          <input
            type="password"
            name=""
            required=""
            placeholder="Enter Password"
            onChange={(e) => {
              setPassword(e.target.value);
            }}
          />
          <label>Password</label>
        </div>
        <div className="user-box">
          <input
            type="number"
            name=""
            required=""
            placeholder="Enter Mobile Number"
            minLength={10}
            maxLength={10}
            onChange={(e) => {
              setMobileNo(e.target.value);
            }}
          />
          <label>Mobile Number</label>
        </div>
        <div className="pt-1 mb-4">
          <button
            onClick={signUpUser}
            className="btn btn-success"
            type="button"
          >
            SignUp
          </button>
          &nbsp;&nbsp;&nbsp;
          <button className="btn btn-secondary" type="reset">
            Clear
          </button>
        </div>

        <p className="mb-5 pb-lg-2">
          Already have an account <a href="/signin">Login here</a>
        </p>
      </form>
    </div>
  );
}
