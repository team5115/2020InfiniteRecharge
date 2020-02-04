package io.github.oblarg.oblog;

import edu.wpi.first.wpilibj.shuffleboard.ComplexWidget;

import java.util.Map;

class WrappedComplexWidget implements ComplexWidgetWrapper {

  ComplexWidget widget;

  WrappedComplexWidget(ComplexWidget widget) {
    this.widget = widget;
  }

  @Override
  public ComplexWidgetWrapper withProperties(Map<String, Object> properties) {
    widget.withProperties(properties);
    return this;
  }

  @Override
  public ComplexWidgetWrapper withWidget(String widgetType) {
    widget = widget.withWidget(widgetType);
    return this;
  }

  @Override
  public ComplexWidgetWrapper withPosition(int columnIndex, int rowIndex) {
    widget.withPosition(columnIndex, rowIndex);
    return this;
  }

  @Override
  public ComplexWidgetWrapper withSize(int width, int height) {
    widget.withSize(width, height);
    return this;
  }
}
