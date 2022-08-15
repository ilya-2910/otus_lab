import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import VetSchedule from './vet-schedule';
import VetScheduleDetail from './vet-schedule-detail';
import VetScheduleUpdate from './vet-schedule-update';
import VetScheduleDeleteDialog from './vet-schedule-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={VetScheduleDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={VetScheduleUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={VetScheduleUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={VetScheduleDetail} />
      <ErrorBoundaryRoute path={match.url} component={VetSchedule} />
    </Switch>
  </>
);

export default Routes;
