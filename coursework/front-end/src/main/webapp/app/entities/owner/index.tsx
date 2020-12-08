import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Owner from './owner';
import OwnerDetail from './owner-detail';
import OwnerUpdate from './owner-update';
import OwnerDeleteDialog from './owner-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={OwnerDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={OwnerUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={OwnerUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={OwnerDetail} />
      <ErrorBoundaryRoute path={match.url} component={Owner} />
    </Switch>
  </>
);

export default Routes;
