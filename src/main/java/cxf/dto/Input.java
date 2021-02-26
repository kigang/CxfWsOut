package cxf.dto;

import java.util.List;
import java.util.Objects;

public class Input {

    private Long id;

    private String name;

    private Object object;

    private List<OutPut> outPuts;

    @Override
    public String toString() {
        return "Input{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", outPuts=" + outPuts +
                '}';
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    @Override
    public boolean equals(Object o) {
        System.out.println("重写比较方法：");
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Input input = (Input) o;
        return Objects.equals(id, input.id) &&
                Objects.equals(name, input.name);
    }



    public List<OutPut> getOutPuts() {
        return outPuts;
    }

    public void setOutPuts(List<OutPut> outPuts) {
        this.outPuts = outPuts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
