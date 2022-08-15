import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Pet from './pet';
import PetDetail from './pet-detail';
import PetUpdate from './pet-update';
import PetDeleteDialog from './pet-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={PetDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PetUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PetUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PetDetail} />
      <ErrorBoundaryRoute path={match.url} component={Pet} />
    </Switch>
  </>
);

export default Routes;
