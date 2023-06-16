import React from 'react'
import  '../css/Header.css'
import { Link } from 'react-router-dom'
const Header = () => {
  return (
    
          <section class="h">
            <div cass="container h-100">

                <div className="row h-100 align-items-center">

                <div className="col-md-12 text-center">
                    <div className="title">
                      <br />
                      <br />
                      <br />
                      <br />
                      <br />
                      <br />
                      <br />
                      <br />
                    <h1>THERE IS NO FRIEND AS LOYAL AS A BOOK  </h1>
                    </div>
                    <br/>
                    <p>A reader lives a thousand lives before he dies.
                    </p>
                        <Link class="nav-link" className="btn btn-primary"  to="/signin">Click Me!</Link>
                    
                </div>


                </div>


            </div>

            </section>

         

  )
}

export default Header;
