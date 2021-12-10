package kr.co.gocamping.vo;

import java.io.Serializable;
import java.util.Map;

import lombok.Builder;

@Builder
public class Results implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -5630755505989712288L;
  private boolean success; // 성공 여부
  private String message; // 결과 메시지
  private String code; // 결과 코드
  private Map<String, Object> data; // 결과 데이터

  public Results() {
  }

  public Results(boolean success) {
    this.success = success;
  }

  public Results(boolean success, String message) {
    this.success = success;
    this.message = message;
  }

  public Results(boolean success, String message, String code) {
    this.success = success;
    this.message = message;
    this.code = code;
  }

  public Results(boolean success, String message, String code, Map<String, Object> data) {
    this.success = success;
    this.message = message;
    this.code = code;
    this.data = data;
  }

  public Results(boolean success, Map<String, Object> data) {
    this.success = success;
    this.data = data;
  }

  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Map<String, Object> getData() {
    return data;
  }

  public void setData(Map<String, Object> data) {
    this.data = data;
  }

}
