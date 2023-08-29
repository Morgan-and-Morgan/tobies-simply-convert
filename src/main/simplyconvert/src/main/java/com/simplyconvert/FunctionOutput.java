package com.simplyconvert;

public class FunctionOutput {

  private final Boolean isSuccess;
  private final String errorMessage;
  private final String intakeIds;

  public FunctionOutput(Boolean isSuccess, String errorMessage, String intakeIds) {
    this.isSuccess = isSuccess;
    this.errorMessage = errorMessage;
    this.intakeIds = intakeIds;
  }

  public Boolean isIsSuccess() {
    return this.isSuccess;
  }

  public Boolean getIsSuccess() {
    return this.isSuccess;
  }

  public String getErrorMessage() {
    return this.errorMessage;
  }

  public String getIntakeId() {
    return this.intakeIds;
  }

}
