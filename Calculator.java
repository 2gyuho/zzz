import java.io.*;
import java.util.*;

/**
 * Calculator 클래스의 설명을 작성하세요.
 *
 * @author (2023320015 정은비 2021320011 이규호 2021320021서유정 2020635040 임성훈)
 * @version (2024.10.23)
 */
public class Calculator {
    public static void main(String[] args) {
        HashMap<String, Integer> student_grade = new HashMap<>();
        
        inputFile(student_grade);

        if (student_grade.isEmpty()) {
            System.out.println("유효한 데이터가 없습니다.");
        } else {
            int total_grade = calculateTotalScore(student_grade);
            output(student_grade, total_grade);
        }
    }
    
    public static void inputFile(HashMap<String, Integer> student_grade) {
        try {
            File file = new File("C:\\temp\\inputData2024.txt");
            FileReader filereader = new FileReader(file);
            Scanner scanner = new Scanner(filereader);
            
            if (!scanner.hasNextLine()) {
                System.out.println("파일의 내용이 존재하지 않습니다.");
                return;
            }
            
            while (scanner.hasNextLine()) {
                String words = scanner.nextLine();
                
                String[] data = words.split(" ");
                
                if (data.length != 2) {
                    System.out.println("이름과 점수 사이에 띄어쓰기가 없거나 잘못된 형식입니다.");
                    break;
                }
                
                String name = data[0];
                
                boolean isThereSpecialInName = false;
                for (int i = 0; i < name.length(); i++) {
                    char ch = name.charAt(i);
                    
                    if (!Character.isLetter(ch) && ch != ' ') {
                        isThereSpecialInName = true;
                        break;
                    }
                }
                
                if (isThereSpecialInName) {
                    System.out.println("이름에 특수 문자 혹은 숫자가 포함되어 있습니다.");
                    break;
                }
                
                String gradeStr = data[1];
                
                boolean isThereCharInGrade = false;
                for (int i = 0; i < gradeStr.length(); i++) {
                    if (!Character.isDigit(gradeStr.charAt(i))) {
                        isThereCharInGrade = true;
                        break;
                    }
                }
                
                if (isThereCharInGrade) {
                    System.out.println("점수에는 숫자만 기입할 수 있습니다.");
                    break;
                } else {
                    Integer grade = Integer.parseInt(gradeStr);
                    student_grade.put(name, grade);
                }
            }
            scanner.close();
            filereader.close();
            
        } catch (IOException e) {
            System.out.println("입출력 오류 발생!");
        }
    }

    public static int calculateTotalScore(HashMap<String, Integer> student_grade) {
        int total_grade = 0;

        Iterator<String> iter = student_grade.keySet().iterator();
        while (iter.hasNext()) {
            String key = iter.next();
            Integer value = student_grade.get(key);
            total_grade += value;
        }
        
        return total_grade;
    }
    
    public static ArrayList<String> calculateRanking(HashMap<String, Integer> student_grade) {
        String topStudent = "";
        String bottomStudent = "";
        int maxScore = Integer.MIN_VALUE;
        int minScore = Integer.MAX_VALUE;

        ArrayList<String> sortedStudents = new ArrayList<>();
        List<String> keys = new ArrayList<>(student_grade.keySet());
    
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            int score = student_grade.get(key);
            int index = 0;
        
            while (index < sortedStudents.size() && student_grade.get(sortedStudents.get(index)) > score) {
                index++;
            }
        
            sortedStudents.add(index, key);
        }
        
        return sortedStudents;
    }

    public static void output(HashMap<String, Integer> student_grade, int total_grade) {
        
        ArrayList<String> sortedStudents = calculateRanking(student_grade);
        
        System.out.println("------ 학생 순위 ------");
    
        for (int i = 0; i < sortedStudents.size(); i++) {
            String student = sortedStudents.get(i);
            System.out.println((i + 1) + "등: " + student + " (점수: " + student_grade.get(student) + ")");
        }
        
        float average_grade = (float) total_grade / student_grade.size();
    
        System.out.println();
        System.out.println("------ 계산 결과 ------");
        System.out.println("총점 : " + total_grade);
        System.out.println("평균 : " + average_grade);
    }
}