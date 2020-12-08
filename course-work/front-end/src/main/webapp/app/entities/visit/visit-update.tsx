import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IPet } from 'app/shared/model/pet.model';
import { getEntities as getPets } from 'app/entities/pet/pet.reducer';
import { IVet } from 'app/shared/model/vet.model';
import { getEntities as getVets } from 'app/entities/vet/vet.reducer';
import { getEntity, updateEntity, createEntity, reset } from './visit.reducer';
import { IVisit } from 'app/shared/model/visit.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IVisitUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const VisitUpdate = (props: IVisitUpdateProps) => {
  const [petId, setPetId] = useState('0');
  const [vetId, setVetId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { visitEntity, pets, vets, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/visit');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getPets();
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
        ...visitEntity,
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
          <h2 id="courseWorkApp.visit.home.createOrEditLabel">Create or edit a Visit</h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : visitEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="visit-id">ID</Label>
                  <AvInput id="visit-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="startDateLabel" for="visit-startDate">
                  Start Date
                </Label>
                <AvInput
                  id="visit-startDate"
                  type="datetime-local"
                  className="form-control"
                  name="startDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.visitEntity.startDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="endDateLabel" for="visit-endDate">
                  End Date
                </Label>
                <AvInput
                  id="visit-endDate"
                  type="datetime-local"
                  className="form-control"
                  name="endDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.visitEntity.endDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label for="visit-pet">Pet</Label>
                <AvInput id="visit-pet" type="select" className="form-control" name="pet.id">
                  <option value="" key="0" />
                  {pets
                    ? pets.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="visit-vet">Vet</Label>
                <AvInput id="visit-vet" type="select" className="form-control" name="vet.id">
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
              <Button tag={Link} id="cancel-save" to="/visit" replace color="info">
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
  pets: storeState.pet.entities,
  vets: storeState.vet.entities,
  visitEntity: storeState.visit.entity,
  loading: storeState.visit.loading,
  updating: storeState.visit.updating,
  updateSuccess: storeState.visit.updateSuccess,
});

const mapDispatchToProps = {
  getPets,
  getVets,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(VisitUpdate);
