package ru.otus.core.model;


import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "users")
@ToString
public class User implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "id")
  private long id;

  @Column(name = "name")
  private String name;

  @Column(name = "login")
  private String login;

  @Column(name = "password")
  private String password;

  public User() {
  }

  public User(long id, String name) {
    this.id = id;
    this.name = name;
  }

  @OneToOne(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.EAGER)
  private AddressDataSet addressDataSet;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.EAGER)
  private List<PhoneDataSet> phoneDataSet;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public AddressDataSet getAddressDataSet() {
    return addressDataSet;
  }

  public void setAddressDataSet(AddressDataSet addressDataSet) {
    this.addressDataSet = addressDataSet;
  }

  public List<PhoneDataSet> getPhoneDataSet() {
    return phoneDataSet;
  }

  public void setPhoneDataSet(List<PhoneDataSet> phoneDataSet) {
    this.phoneDataSet = phoneDataSet;
  }

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
