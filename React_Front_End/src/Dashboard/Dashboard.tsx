import React from 'react';
import Navbar from 'react-bootstrap/Navbar';
import { BrowserRouter as Router, Route, Link } from "react-router-dom";
import { userService } from '../_services/user.services';
import userData from '../_models/users.model';

// Images and Icon Imports
import './dashboard.css';
import DEXINO_Icon from '../images/icon.png'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faTools } from '@fortawesome/free-solid-svg-icons'
import { faUtensils} from '@fortawesome/free-solid-svg-icons'

// Other Pages
import {Meals} from '../Meals/Meals'

const routes = [
  {
    path: "/",
    exact: true,
    main: () => <h2>Home</h2>
  },
  {
    path: "/meals",
    main: Meals
  },
  {
    path: "/reports",
    main: () => <h2>REPORTS</h2>
  },
  {
    path: "/settings",
    main: () => <h2>SETTINGS</h2>
  }
];

interface IState {
  user: userData,
  users: {
    loading?: boolean,
    error?: string
  }
}

class Dashboard extends React.Component<{},IState> {
    constructor(props) {
        super(props);

        this.state = {
            user: {
              userName: '',
              password: '',
              firstName: '',
              lastName: ''
            },
            users: {
              loading: false,
              error:''
            }
        };
        this.logout = this.logout.bind(this);
    }

    componentDidMount() {
        this.setState(
          {
            user: JSON.parse(localStorage.getItem('user')||'{}') as userData,
            users: {loading: true}
          }
        );
    }

    logout() {
      userService.logout();
      window.location.reload(true);
    }

    render() {
        const { user, users } = this.state;
        return (
            <div>
                <Router>
                <Navbar bg="dark" className="navbar navbar-dark fixed-top bg-dark flex-md-nowrap p-0 shadow">
                <Navbar.Brand>
                  <img
                    src={DEXINO_Icon}
                    width="30"
                    height="30"
                    className="d-inline-block align-top"
                    alt="DEXINO LOGO"
                  />&nbsp;Dexino Calorie Tracker
                </Navbar.Brand>
                <div className="w-100"></div>
                <ul className="navbar-nav px-3">
                  <li className="nav-item text-nowrap">
                    <div onClick={this.logout}>Sign Out</div>  
                  </li>
                </ul>
                </Navbar>
        
                <div className="container-fluid">
                  <div className="row">
                    <nav className="col-md-2 d-none d-md-block bg-light sidebar">
                      <div className="sidebar-sticky">   
                      <h6 className="sidebar-heading d-flex justify-content-between align-items-center px-3 mt-4 mb-1 text-muted">
                        <span><FontAwesomeIcon icon={faTools} />&nbsp;Administration</span>
                      </h6>
                      <ul className="nav flex-column mb-2">
                        <li className="nav-item">
                          <a className="nav-link" href="#">
                            <span data-feather="file-text"></span>
                            Current month
                          </a>
                        </li>
                      </ul>
                      <h6 className="sidebar-heading d-flex justify-content-between align-items-center px-3 mt-4 mb-1 text-muted">
                        <span><FontAwesomeIcon icon={faUtensils} />&nbsp;My Stats</span>
                      </h6>
                      <ul className="nav flex-column mb-2">
                        <li className="nav-item">
                          <Link className="nav-link" to="/meals">Meals</Link>
                        </li>
                        <li className="nav-item">
                          <a className="nav-link">
                            Reports
                          </a>
                        </li>
                        <li className="nav-item">
                          <a className="nav-link">
                            Settings
                          </a>
                        </li>
                      </ul>
        
        
                      </div>
                    </nav>
        
                    <main role="main" className="col-md-9 ml-sm-auto col-lg-10 px-4">
                      <div className="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                        {routes.map((route, index) => (
                          <Route
                            key={index}
                            path={route.path}
                            exact={route.exact}
                            component={route.main}
                          />
                        ))}
                      </div>
                      <div>Copyright© Dexino, Inc.  All Rights Reserved</div>
                    </main>
                  </div>
                </div>
        
              </Router>
            </div>
        );
    }
}

export { Dashboard };