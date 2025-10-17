import React from 'react';
import { IonApp, IonRouterOutlet } from '@ionic/react';
import { IonReactRouter } from '@ionic/react-router';
import { Route, Redirect } from 'react-router-dom';
import BooksList from './pages/BooksList';
import BookDetail from './pages/BookDetail';

import '@ionic/react/css/core.css';
import '@ionic/react/css/normalize.css';
import '@ionic/react/css/structure.css';
import '@ionic/react/css/typography.css';

const App: React.FC = () => (
    <IonApp>
        <IonReactRouter>
            <IonRouterOutlet>
                <Route exact path="/books" component={BooksList} />
                <Route exact path="/books/:id" component={BookDetail} />
                <Route exact path="/" render={() => <Redirect to="/books" />} />
            </IonRouterOutlet>
        </IonReactRouter>
    </IonApp>
);

export default App;
