/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MIP;

import static MIP.exportExcel.writeExcel;
import static MIP.importExcel.readExcel;
import com.google.ortools.Loader;
import com.google.ortools.linearsolver.MPConstraint;
import com.google.ortools.linearsolver.MPObjective;
import com.google.ortools.linearsolver.MPSolver;
import com.google.ortools.linearsolver.MPVariable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dhmty
 */
public class main {
    
    public static int rowIndex=0;//đếm sos hàng đucợ ghi
    
    public static void phanCong(List<tmpObj> listTmp,shortShift ss,String [] NV,int [] ts_CV,double[] time_CV,int shortShiftTime){
        Loader.loadNativeLibraries();
        int numNV=NV.length;
        int numCV=time_CV.length;
        for (int i=0;i<ts_CV.length;i++){
            if (ts_CV[i]==100) ts_CV[i]=numNV;
        }
  
        System.out.println("\tBANG NHÂN VIÊN - THOI GIAN CONG VIEC ");
        System.out.print("NV/CV\t");
        for (int i=0;i<numCV;i++){
            System.out.print("CV"+i+"\t");
        }
        System.out.println("");
        for (int i=0;i<numNV;i++){
            System.out.print(NV[i]+"\t");
            for (int j=0;j<numCV;j++){
                System.out.print(time_CV[j]+"\t");
            }
            System.out.println("");
        }
        
        // Thuật toán giải quyết
        // Create the linear solver with the SCIP backend.
        MPSolver solver = MPSolver.createSolver("SCIP");
        if (solver == null) {
          System.out.println("Could not create solver SCIP");
          return;
        }
        // Variables
        // Tạo mảng 2 chiều có giá tị 0 hoặc 1, 1 nếu NV làm CV đó
        MPVariable[][] x = new MPVariable[numNV][numCV];
        for (int i = 0; i < numNV; ++i) {
          for (int j = 0; j < numCV; ++j) {
            if (ts_CV[j]==numNV) x[i][j] = solver.makeIntVar(1, 1, ""); 
            else
                x[i][j] = solver.makeIntVar(0, 1, "");
          }
        }
        // tạo Constraints : rằng buộc bài toán

        for (int j = 0; j < numCV; ++j) {
          MPConstraint  constraint = solver.makeConstraint(1,ts_CV[j], ""); 
          for (int i = 0; i < numNV; ++i) {
            constraint.setCoefficient(x[i][j], 1);
          }
        }
       
       for (int i = 0; i < numNV; ++i) {
          MPConstraint constraint = solver.makeConstraint(0,shortShiftTime, "");
          for (int j = 0; j < numCV; ++j) {
            constraint.setCoefficient(x[i][j],time_CV[j]);
          }
        }
        // Objective _ đối tượng
        // gán mảng chi phí vào mảng đối tượng sau đó thực hiện tính toán 
        MPObjective objective = solver.objective();
        for (int i = 0; i < numNV; ++i) {
          for (int j = 0; j < numCV; ++j) {
            objective.setCoefficient(x[i][j], time_CV[j]);
          }
        }
        solver.solve();
        objective.setMaximization();// set tìm giá trị nhỏ nhất theo thuật toán với mỗi task
        MPSolver.ResultStatus resultStatus = solver.solve(); // kết quả dựa vào các giá trị và rằng buộc + đối tượng cost
        // Check that the problem has a feasible solution.
        if (resultStatus == MPSolver.ResultStatus.OPTIMAL || resultStatus == MPSolver.ResultStatus.FEASIBLE)  {
                //kết quả của thuật toán từng điểm
                for (int i = 0; i < numNV; ++i) {
                        for (int j = 0; j < numCV; ++j) {
                            System.out.print(x[i][j].solutionValue()+"\t");
                        }
                        System.out.println("");
                    }
                System.out.println("Total Time: " + objective.value() + "\n");
                    for (int i = 0; i < numNV; ++i) {
                        int s=0;
                        for (int j = 0; j < numCV; ++j) {
                          if (x[i][j].solutionValue() > 0.5) {
                              s+=time_CV[j];
                                   System.out.println(
                                      "Worker " + i + " assigned to task " + j + ".  Time = " + time_CV[j]);
                                   rowIndex++;
                                   System.out.println(rowIndex);
                                   // gán mỗi hàng vào một arrayList tạm
                                   tmpObj t=new tmpObj();
                                   t.setDate(ss.getDate());
                                   t.setMaST(ss.getMaST());
                                   t.setMaCL(ss.getMaCL());
                                   t.setMaCN(ss.getMaCN());
                                   t.setIdNV(i);
                                   t.setTenCV(ss.listJob.dsJob.get(j).getTen());
                                   t.setTgian(time_CV[j]);
                                   t.setGioBD(ss.getGio_bd());
                                   t.setPhutBD(ss.getPhut_bd());
                                   listTmp.add(t);
                           }
                        }
                        System.out.println("Tong : "+s);
                    }
            } else 
            {
              System.err.println("No solution found.");
            }
    }
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
       
       writeExcel(listTmp,excelFilePath_out);
       
    }
    private main(){}
    
}
