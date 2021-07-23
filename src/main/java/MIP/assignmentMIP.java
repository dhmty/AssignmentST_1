package MIP;

import com.google.ortools.Loader;
import com.google.ortools.linearsolver.MPConstraint;
import com.google.ortools.linearsolver.MPObjective;
import com.google.ortools.linearsolver.MPSolver;
import com.google.ortools.linearsolver.MPVariable;
import java.util.List;

/**
 *
 * @author dhmty
 */
public class assignmentMIP {

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
          MPConstraint  constraint = solver.makeConstraint(ts_CV[j],ts_CV[j], "");
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
                                   // gán mỗi hàng vào một arrayList phân công
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
}
