package MIP;

import static MIP.assignmentMIP.phanCong;
import static MIP.exportExcel.writeExcel;
import static MIP.importExcel.readExcel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dhmty
 */
public class main {
    public static void main(String args[]) throws IOException {
        
 // Bộ thử 1-11-6581      
//        String [] NV={"1","2","3"};
//        int [] ts_CV ={1,2,1,100,2,1,1,100,1,1};
//        double[] time_CV = {4,1,16,30,13,16,5,1,2,1};
//        int shortShiftTime=60;

//Bộ thử 1-12 2882
//        String [] NV={"1","2","3","4"};
//        int [] ts_CV ={1,100,1,1,1,1,1};
//        double[] time_CV = {1,11,8,22,42,49,9};
//        int shiftTime=90;
//     
// bộ thử 1-11 2882
//        String [] NV={"1","2","3","4"};
//        int [] ts_CV ={2,1,100,1,1,1,1,1,1,1,100};
//        double[] time_CV = {14,21,1.25,1,12,24,2,2,6,1,25};
//        int shortShiftTime=60;
   //phanCong(NV, ts_CV, time_CV, shortShiftTime);     
        
        //final int shortShiftTime=0;
        final List<tmpObj> listTmp=new ArrayList<>(); // list ghi tạm các hàng
        final String excelFilePath_out = "data_Export/TemplateExport_CalAssignmentData_Fresher.xlsx";
        final String excelFilePath = "data_Import/TemplateImport_CalAssignmentData_Fresher.xlsx";
        final List<shiftModel> listShift =readExcel(excelFilePath);
    
        listShift.forEach(sh->{
             sh.listSS.dsShortList.forEach(ss->{
                System.out.println("Ca Lon: "+sh.getMaCL()+" Sieu Thi : "+sh.getMaST()+" Ca Nho: "+ss.getMaCN());
                int n=sh.getHeadCount();
                String [] NV=new String[n];
                for (int i=0;i<n;i++){
                    NV[i]=String.valueOf(i);
                }
                int shortShiftTime=ss.getTgian();
                int i=0;
                int [] ts_CV =new int[ss.listJob.dsJob.size()];
                double[] time_CV = new double[ss.listJob.dsJob.size()];
                for (int j=0;j<ss.listJob.dsJob.size();j++){
                    job jb=ss.listJob.dsJob.get(j);
                    double tmp=0;
                    ts_CV[i]=jb.getSoNg();
                    if (jb.getSoNg()==100) tmp=(double)jb.getSoPh()/n;
                    else 
                        tmp =(double)jb.getSoPh()/jb.getSoNg();
                    tmp=(double)Math.round(tmp*100)/100;
                    time_CV[i]=tmp;
                    i++;
                }
                     phanCong(listTmp,ss,NV, ts_CV, time_CV, shortShiftTime);
             });
             
        });
       // viết ra file excel
       writeExcel(listTmp,excelFilePath_out);
       
    }
    private main(){}
    
}
