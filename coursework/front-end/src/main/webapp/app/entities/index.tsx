import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Visit from './visit';
import Pet from './pet';
import Owner from './owner';
import Vet from './vet';
import VetSchedule from './vet-schedule';
import PetType from './pet-type';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}visit`} component={Visit} />
      <ErrorBoundaryRoute path={`${match.url}pet`} component={Pet} />
      <ErrorBoundaryRoute path={`${match.url}owner`} component={Owner} />
      <ErrorBoundaryRoute path={`${match.url}vet`} component={Vet} />
      <ErrorBoundaryRoute path={`${match.url}vet-schedule`} component={VetSchedule} />
      <ErrorBoundaryRoute path={`${match.url}pet-type`} component={PetType} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
