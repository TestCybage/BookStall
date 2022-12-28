import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import { ToastContainer } from "react-toastify";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import { AllBooks } from './components/admin/AllBooks';

function App() {
  return (
    <div className="App"> 
    {/* <BrowserRouter>
        <Routes>
        <Route path="/getAllBooks" element={<AllBooks/>} />


        </Routes>
    </BrowserRouter> */}
    <AllBooks/>
    <ToastContainer theme="colored" />
    </div>
  );
}

export default App;
