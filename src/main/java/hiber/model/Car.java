package hiber.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Embeddable()
public class Car {

  @Column()
  private String model;

  @Column()
  private int series;

  public Car() {
  }

  public Car(String model, int series) {
    this.model = model;
    this.series = series;
  }

  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public int getSeries() {
    return series;
  }

  public void setSeries(int series) {
    this.series = series;
  }

  @Override
  public String toString() {
    return String.format("car: model %s, series %d", model, series);
  }
}
