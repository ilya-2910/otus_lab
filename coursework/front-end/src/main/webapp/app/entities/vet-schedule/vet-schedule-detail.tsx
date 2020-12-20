import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './vet-schedule.reducer';
import { IVetSchedule } from 'app/shared/model/vet-schedule.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IVetScheduleDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const VetScheduleDetail = (props: IVetScheduleDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { vetScheduleEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="courseworkApp.vetSchedule.detail.title">VetSchedule</Translate> [<b>{vetScheduleEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="startDate">
              <Translate contentKey="courseworkApp.vetSchedule.startDate">Start Date</Translate>
            </span>
          </dt>
          <dd>
            {vetScheduleEntity.startDate ? <TextFormat value={vetScheduleEntity.startDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="endDate">
              <Translate contentKey="courseworkApp.vetSchedule.endDate">End Date</Translate>
            </span>
          </dt>
          <dd>
            {vetScheduleEntity.endDate ? <TextFormat value={vetScheduleEntity.endDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <Translate contentKey="courseworkApp.vetSchedule.vet">Vet</Translate>
          </dt>
          <dd>{vetScheduleEntity.vet ? vetScheduleEntity.vet.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/vet-schedule" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/vet-schedule/${vetScheduleEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ vetSchedule }: IRootState) => ({
  vetScheduleEntity: vetSchedule.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(VetScheduleDetail);
