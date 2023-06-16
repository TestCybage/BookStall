import React from "react";
import "./signIn.css";
import { useState } from "react";
import { toast } from "react-toastify";
import axios from "axios";
import { useNavigate } from "react-router-dom";

function SignIn() {
  const navigate = useNavigate();
  const [userName, setUserName] = useState("");
  const [userPassword, setUserPassword] = useState("");

  const signinUser = () => {
    if (userName.length === 0) {
      toast.warning("please enter Username");
    } else if (userPassword.length === 0) {
      toast.warning("please enter password");
    } else {
      const body = new FormData();
      body.append("userName", userName);
      body.append("userPassword", userPassword);

      axios
        .post(
          "http://localhost:8085/authenticate",
          { userName: userName, userPassword: userPassword },
          { "Content-Type": "application/json" }
        )
        .then((response) => {
          const result = response.data;
          console.log(result.user);
          console.log(result.jwtToken);
          const roles = result.user.role;
          function hasRole(roles, targetRoleName) {
            for (let role of roles) {
              if (role.roleName === targetRoleName) {
                return true;
              }
            }
            return false;
          }
          console.log("login succesful");
          console.log(result.user.userName);
          sessionStorage.setItem("userName", result.user.userName);
          sessionStorage.setItem("email", result.user.email);
          localStorage.setItem("jwtToken", result.jwtToken);
          sessionStorage.setItem("name", result.user.name);
          console.log(result.user.name);
          if (hasRole(roles, "USER") === true) {
            navigate("/signin");
            toast.success("Welcome to Book Shop");
          } else if (hasRole(roles, "ADMIN") === true) {
            navigate("/adminHome");
            toast.success("Welcome Administrator");
          }
        })
        .catch((error) => {
          console.error(error.response.data.message);
          toast.error(error.response.data.message);
        });
    }
  };
  return (
    <div className="login-box">
      <h2>Login</h2>
      <form>
        <div className="user-box">
          <input
            type="text"
            name=""
            required=""
            onChange={(e) => {
              setUserName(e.target.value);
            }}
          />
          <label>Username</label>
        </div>
        <div className="user-box">
          <input
            type="password"
            name=""
            required=""
            onChange={(e) => {
              setUserPassword(e.target.value);
            }}
          />
          <label>Password</label>
        </div>
        <div className="pt-1 mb-4">
          <a onClick={signinUser} type="button">
            {" "}
            Login
          </a>
        </div>

        <a className="small text-muted" href="forgotPassword">
          Forgot password?
        </a>
        <p className="mb-5 pb-lg-2">
          Don't have an account? <a href="signup">Register here</a>
        </p>
      </form>
    </div>
  );
}

export default SignIn;
