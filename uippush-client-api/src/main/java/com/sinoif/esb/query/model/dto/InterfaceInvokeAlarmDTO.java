package com.sinoif.esb.query.model.dto;

import java.io.Serializable;

/**
 * 接口预警DTO
 */
public class InterfaceInvokeAlarmDTO implements Serializable {

    public class Wrapper implements Serializable{
        String interfaceId;
        String status;

        public String getInterfaceId() {
            return interfaceId;
        }

        public void setInterfaceId(String interfaceId) {
            this.interfaceId = interfaceId;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

    }

    Wrapper _id;
    String interfaceName;
    int sum;

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public Wrapper get_id() {
        return _id;
    }

    public void set_id(Wrapper _id) {
        this._id = _id;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }
}
