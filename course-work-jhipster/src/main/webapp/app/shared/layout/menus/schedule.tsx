import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Translate, translate } from 'react-jhipster';
import { NavDropdown } from './menu-components';
import AdminMenu from "app/shared/layout/menus/admin";
import { NavItem, NavLink, NavbarBrand } from 'reactstrap';
import { NavLink as Link } from 'react-router-dom';

export const ScheduleMenu = props => (
  <NavItem>
    <NavLink tag={Link} to="/" className="d-flex align-items-center">
      <FontAwesomeIcon icon="eye" />
      <span>
        <Translate contentKey="global.menu.schedule">Home</Translate>
      </span>
    </NavLink>
  </NavItem>
);
