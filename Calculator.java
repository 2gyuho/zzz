import java.io.*;
import java.util.*;

/**
 * Calculator 클래스는 학생들의 이름과 성적을 입력 파일로부터 받아와
 * 성적을 처리하고, 총점,평균, 학생들의 점수 순위를 계산하여 출력하는 프로그램입니다.
 * 
 * @author (2023320015 정은비 2021320011 이규호 2021320021서유정 2020635040 임성훈)
 * @version (2024.10.23)
 */
public class Calculator {
    
    
    public static void main(String[] args) {
        HashMap<String, Integer> student_grade = new HashMap<>();

        inputFile(student_grade);

        if (student_grade.isEmpty()) {
            return;
        }

        int total_grade = calculateTotalScore(student_grade);
        output(student_grade, total_grade);
    }

    /**
     * inputFile()는 "C:\\temp\\inputData2024.txt"경로에 있는 파일을 읽고 HashMap인 student_grade에 저장합니다.
     *
     * @param  student_grade  학생이름과 성적 데이터
     * @return    void
     */
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
        } catch (NumberFormatException e) {
            System.out.println("문자형은 숫자형으로 변경할 수 없습니다.");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("문자형은 숫자형으로 변경할 수 없습니다.");
        }

    }

    /**
     * calculateTotalScore() 메소드는 student_grade에 있는 학생 이름(key)으로 점수(value)를 받아 total_grade에 합산하여 리턴합니다.
     *
     * @param  student_grade  학생이름과 성적 데이터
     * @return    student_grade의 점수(value)를 모두 더한 정수
     */
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

    /**
     * calculateRanking() 메소드는 student_grade에 있는 학생 이름(key)으로 점수(value)를 받아 점수(value)를 비교하여 점수 순서대로 sortedStudents에 저장합니다.
     *
     * @param  student_grade  학생이름과 성적 데이터
     * @return    학생들의 점수를 순서대로 정렬한 리스트(ArrayList)
     */
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

    /**
     * output() 메소드는 calculateTotalScore() 와 calculateRanking()에서 계산한 결과를 출력합니다.
     *
     * @param  student_grade  학생이름과 성적 데이터, total_grade 총점
     * @return    void
     */
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
