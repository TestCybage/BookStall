import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import { ToastContainer } from "react-toastify";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import TemporaryDrawer from './components/admin/Navbar/TemporaryDrawer';
import { AllBooks } from './components/admin/Book/AllBooks';


function App() {
  return (
    <div className="App"> 
    <BrowserRouter>
        <Routes>
        <Route path="/" element={<TemporaryDrawer/>} />  
        <Route path="/getAllBooks" element={<AllBooks/>} />


        </Routes>
    </BrowserRouter>
    <ToastContainer theme="colored" />
    </div>
  );
}

export default App;
