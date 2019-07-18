import React from "react";
import { BrowserRouter as Router, Route, Link } from "react-router-dom";
import { PrivateRoute } from './_components/PrivateRoute';
import { Dashboard } from './Dashboard/Dashboard';
import { Login } from './Login/Login';

const App: React.FC = () => {
  return (
    <Router>
        <div>
            <PrivateRoute exact path="/" component={Dashboard} />
            <Route path="/login" component={Login} />
        </div>
    </Router>
  );
}

export default App;
