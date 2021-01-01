import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Vet from './vet';
import VetDetail from './vet-detail';
import VetUpdate from './vet-update';
import VetDeleteDialog from './vet-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={VetDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={VetUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={VetUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={VetDetail} />
      <ErrorBoundaryRoute path={match.url} component={Vet} />
    </Switch>
  </>
);

export default Routes;
