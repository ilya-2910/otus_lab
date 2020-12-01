import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './pet.reducer';
import { IPet } from 'app/shared/model/pet.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPetDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PetDetail = (props: IPetDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { petEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="courseWorkApp.pet.detail.title">Pet</Translate> [<b>{petEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="name">
              <Translate contentKey="courseWorkApp.pet.name">Name</Translate>
            </span>
          </dt>
          <dd>{petEntity.name}</dd>
          <dt>
            <span id="type">
              <Translate contentKey="courseWorkApp.pet.type">Type</Translate>
            </span>
          </dt>
          <dd>{petEntity.type}</dd>
          <dt>
            <Translate contentKey="courseWorkApp.pet.owner">Owner</Translate>
          </dt>
          <dd>{petEntity.owner ? petEntity.owner.name : ''}</dd>
        </dl>
        <Button tag={Link} to="/pet" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/pet/${petEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ pet }: IRootState) => ({
  petEntity: pet.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PetDetail);
