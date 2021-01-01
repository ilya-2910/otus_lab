import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './owner.reducer';
import { IOwner } from 'app/shared/model/owner.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IOwnerDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const OwnerDetail = (props: IOwnerDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { ownerEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="courseworkApp.owner.detail.title">Owner</Translate> [<b>{ownerEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="name">
              <Translate contentKey="courseworkApp.owner.name">Name</Translate>
            </span>
          </dt>
          <dd>{ownerEntity.name}</dd>
          <dt>
            <span id="address">
              <Translate contentKey="courseworkApp.owner.address">Address</Translate>
            </span>
          </dt>
          <dd>{ownerEntity.address}</dd>
          <dt>
            <span id="phone">
              <Translate contentKey="courseworkApp.owner.phone">Phone</Translate>
            </span>
          </dt>
          <dd>{ownerEntity.phone}</dd>
        </dl>
        <Button tag={Link} to="/owner" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/owner/${ownerEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ owner }: IRootState) => ({
  ownerEntity: owner.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(OwnerDetail);
