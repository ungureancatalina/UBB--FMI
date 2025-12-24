import React from 'react';
import { IonApp, IonRouterOutlet } from '@ionic/react';
import { IonReactRouter } from '@ionic/react-router';
import { Redirect, Route, RouteProps } from 'react-router-dom';
import BooksList from './pages/BooksList';
import BookDetail from './pages/BookDetail';
import Login from './pages/Login';
import { isAuthenticated } from './services/auth';

import '@ionic/react/css/core.css';
import '@ionic/react/css/normalize.css';
import '@ionic/react/css/structure.css';
import '@ionic/react/css/typography.css';
import './theme/variables.css';

interface PrivateRouteProps extends RouteProps {
    component: React.ComponentType<Record<string, unknown>>;
}

const PrivateRoute: React.FC<PrivateRouteProps> = ({ component: Component, ...rest }) => (
    <Route
        {...rest}
        render={(props) =>
            isAuthenticated() ? <Component {...props} /> : <Redirect to="/login" />
        }
    />
);

const App: React.FC = () => (
    <IonApp>
        <IonReactRouter>
            <IonRouterOutlet>
                <Route exact path="/login" component={Login} />
                <PrivateRoute exact path="/books" component={BooksList} />
                <PrivateRoute exact path="/books/:id" component={BookDetail} />
                <Route
                    exact
                    path="/"
                    render={() => <Redirect to={isAuthenticated() ? '/books' : '/login'} />}
                />
            </IonRouterOutlet>
        </IonReactRouter>
    </IonApp>
);

export default App;
