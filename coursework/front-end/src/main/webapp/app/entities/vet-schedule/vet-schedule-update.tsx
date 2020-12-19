import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IVet } from 'app/shared/model/vet.model';
import { getEntities as getVets } from 'app/entities/vet/vet.reducer';
import { getEntity, updateEntity, createEntity, reset } from './vet-schedule.reducer';
import { IVetSchedule } from 'app/shared/model/vet-schedule.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IVetScheduleUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const VetScheduleUpdate = (props: IVetScheduleUpdateProps) => {
  const [vetId, setVetId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { vetScheduleEntity, vets, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/vet-schedule');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getVets();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.startDate = convertDateTimeToServer(values.startDate);
    values.endDate = convertDateTimeToServer(values.endDate);

    if (errors.length === 0) {
      const entity = {
        ...vetScheduleEntity,
        ...values,
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="courseworkApp.vetSchedule.home.createOrEditLabel">Create or edit a VetSchedule</h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : vetScheduleEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="vet-schedule-id">ID</Label>
                  <AvInput id="vet-schedule-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="startDateLabel" for="vet-schedule-startDate">
                  Start Date
                </Label>
                <AvInput
                  id="vet-schedule-startDate"
                  type="datetime-local"
                  className="form-control"
                  name="startDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.vetScheduleEntity.startDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="endDateLabel" for="vet-schedule-endDate">
                  End Date
                </Label>
                <AvInput
                  id="vet-schedule-endDate"
                  type="datetime-local"
                  className="form-control"
                  name="endDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.vetScheduleEntity.endDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label for="vet-schedule-vet">Vet</Label>
                <AvInput id="vet-schedule-vet" type="select" className="form-control" name="vet.id">
                  <option value="" key="0" />
                  {vets
                    ? vets.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/vet-schedule" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  vets: storeState.vet.entities,
  vetScheduleEntity: storeState.vetSchedule.entity,
  loading: storeState.vetSchedule.loading,
  updating: storeState.vetSchedule.updating,
  updateSuccess: storeState.vetSchedule.updateSuccess,
});

const mapDispatchToProps = {
  getVets,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(VetScheduleUpdate);
