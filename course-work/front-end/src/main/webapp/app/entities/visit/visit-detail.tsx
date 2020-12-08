import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './visit.reducer';
import { IVisit } from 'app/shared/model/visit.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IVisitDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const VisitDetail = (props: IVisitDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { visitEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          Visit [<b>{visitEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="startDate">Start Date</span>
          </dt>
          <dd>{visitEntity.startDate ? <TextFormat value={visitEntity.startDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="endDate">End Date</span>
          </dt>
          <dd>{visitEntity.endDate ? <TextFormat value={visitEntity.endDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>Pet</dt>
          <dd>{visitEntity.pet ? visitEntity.pet.id : ''}</dd>
          <dt>Vet</dt>
          <dd>{visitEntity.vet ? visitEntity.vet.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/visit" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/visit/${visitEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ visit }: IRootState) => ({
  visitEntity: visit.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(VisitDetail);
