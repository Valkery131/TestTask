import java.util.ArrayList;
import java.util.Arrays;

public class solution {

    public static void main(String[] args) {

        int[] inputArr = new int[]{0, 5, 10, 0, 11, 14, 13, 4, 11, 8, 8, 7, 1, 4, 12, 11};
        System.out.println(Arrays.toString(inputArr));
        ArrayList<String> listCombos = new ArrayList<>();   //список комбинаций значений
        int step = 0;   //количество шагов

        listCombos.add(Arrays.toString(inputArr));  //добавляем начальную комбинацию значений в список
        //выполняем алгоритм до обнаружения повтряющейся комбинации значений
        while (true) {
            step++;

            //находим первое вхождение максимального значения в массиве и его индекс
            int max = inputArr[0];
            int index = 0;
            for (int i = 1; i < inputArr.length; i++) {
                if (inputArr[i] > max) {
                    max = inputArr[i];
                    index = i;
                }
            }

            //обнуляем ячейку с максимальным значением и перераспрееляем это значение по другим ячейкам, начиная со следующей
            inputArr[index] = 0;
            for (int i = max; i > 0; i--) {

                if (index + 1 >= inputArr.length) { //при достижении последнего элемента массива переходим к первому
                    index = 0;
                }
                else {
                    index++;
                }

                inputArr[index]++;
            }

            String newCombination = Arrays.toString(inputArr);  //переводим в строку новую комбинацию значений

            //проверяем, встречалась ли уже такая комбинация значений и, если нет - добавляем её в список,
            if (listCombos.contains(newCombination)) {   //если строка встречается - выводим количество шагов и длину цикла, после чего завершаем цикл
                System.out.println("Шагов до обнаружения цикла - " + step + System.lineSeparator()
                        + "Длина цикла - " + (listCombos.size() - listCombos.indexOf(newCombination)));
                break; //прерываем цикл
            } else {  //иначе добавляем комбинацию в список
                listCombos.add(newCombination);
            }
        }
    }
}
