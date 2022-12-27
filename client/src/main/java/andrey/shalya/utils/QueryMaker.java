package andrey.shalya.utils;

import java.util.List;

public class QueryMaker {
    private String sort;
    private String sortType;
    private int page;
    private int pageSize;


    private List<Long> idFilters;
    private List<Double> xFilters;

    public void addIdFilter(Long id){
        idFilters.add(id);
    }
    public void addXFilter(Double x){
        xFilters.add(x);
    }

//    public String makeQuery(){
//        String q = "SELECT * FROM vehicles v\n" +
//                "         inner join coordinates c on c.coord_id = v.coord_id\n" +
//                "         where id>0 and ";
//
//        if(idFilters.size() == 1){
//            q = q.concat(String.format(" id = %d ",idFilters.get(0)));
//        }
//        if(idFilters.size()>=2){
//            q = q.concat("(");
//            for (int i = 0; i < idFilters.size(); i++) {
//                q = q.concat("")
//            }
//            q = q.concat(")");
//        }
//    }
}
