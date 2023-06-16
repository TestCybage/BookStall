import "./App.css";
import "bootstrap/dist/css/bootstrap.min.css";
import { ToastContainer } from "react-toastify";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import Header from "./components/common/header";
import SignIn from "./components/user/SignIn/signIn";
import SignUp from "./components/user/SignUp/signUp";
import 'react-toastify/dist/ReactToastify.css'
import { AdminHome } from "./components/admin/Admin Home/adminHome";
import { AddBook } from "./components/admin/Add Book/AddBook";
import AllBooks from "./components/admin/All Books/allBooks";
import EditBooks from "./components/admin/Edit Book/editBook";
import UnblockUser from "./components/admin/Unblock Users/unblockUsers";
import { AddAuthor } from "./components/admin/Add Author/addAuthor";
import AllAuthors from "./components/admin/All Authors/allAuthors";


function App() {
  return (
    <div className="App">
      <BrowserRouter>
        <Routes>
        <Route exact path='/' element={<Header/>}/>
        <Route exact path='/signin' element={<SignIn/>}/>
        <Route exact path='/signup' element={<SignUp/>}/>
        <Route exact path='/adminHome' element={<AdminHome/>}/>
        <Route exact path="/addBook" element={<AddBook/>}/>
        <Route exact path="/admin/allBooks" element={<AllBooks/>}/>
        <Route exact path="/editBook" element={<EditBooks/>}/>
        <Route exact path="/unblockUser" element={<UnblockUser/>}/>
        <Route exact path="/addAuthor" element={<AddAuthor/>}/>
        <Route exact path="/admin/allAuthors" element={<AllAuthors/>}/>
        </Routes>
      </BrowserRouter>
      <ToastContainer theme="colored" />
    </div>
  );
}

export default App;
