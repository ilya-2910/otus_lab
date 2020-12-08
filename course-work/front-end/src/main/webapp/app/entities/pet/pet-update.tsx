import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IOwner } from 'app/shared/model/owner.model';
import { getEntities as getOwners } from 'app/entities/owner/owner.reducer';
import { getEntity, updateEntity, createEntity, reset } from './pet.reducer';
import { IPet } from 'app/shared/model/pet.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IPetUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PetUpdate = (props: IPetUpdateProps) => {
  const [ownerId, setOwnerId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { petEntity, owners, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/pet');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getOwners();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...petEntity,
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
          <h2 id="courseWorkApp.pet.home.createOrEditLabel">Create or edit a Pet</h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : petEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="pet-id">ID</Label>
                  <AvInput id="pet-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nameLabel" for="pet-name">
                  Name
                </Label>
                <AvField id="pet-name" type="text" name="name" />
              </AvGroup>
              <AvGroup>
                <Label id="typeLabel" for="pet-type">
                  Type
                </Label>
                <AvInput id="pet-type" type="select" className="form-control" name="type" value={(!isNew && petEntity.type) || 'Dog'}>
                  <option value="Dog">Dog</option>
                  <option value="Cat">Cat</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="pet-owner">Owner</Label>
                <AvInput id="pet-owner" type="select" className="form-control" name="owner.id">
                  <option value="" key="0" />
                  {owners
                    ? owners.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/pet" replace color="info">
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
  owners: storeState.owner.entities,
  petEntity: storeState.pet.entity,
  loading: storeState.pet.loading,
  updating: storeState.pet.updating,
  updateSuccess: storeState.pet.updateSuccess,
});

const mapDispatchToProps = {
  getOwners,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PetUpdate);
