import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import PetType from './pet-type';
import PetTypeDetail from './pet-type-detail';
import PetTypeUpdate from './pet-type-update';
import PetTypeDeleteDialog from './pet-type-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={PetTypeDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PetTypeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PetTypeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PetTypeDetail} />
      <ErrorBoundaryRoute path={match.url} component={PetType} />
    </Switch>
  </>
);

export default Routes;
