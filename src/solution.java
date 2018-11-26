import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class solution {

    public static void main(String[] args) {

        int[] inputArr = new int[]{0, 5, 10, 0, 11, 14, 13, 4, 11, 8, 8, 7, 1, 4, 12, 11};
        System.out.println(Arrays.toString(inputArr));
        ArrayList<String> listCombos = new ArrayList<>();   //список комбинаций значений для поиска длины цикла
        HashSet<String> setCombos = new HashSet<>();    //множество значений для проверки того, встречалась ли комбинация
        int step = 0;   //количество шагов
        int n = inputArr.length;  //длина массива значений

        listCombos.add(Arrays.toString(inputArr));  //добавляем начальную комбинацию значений в список
        //выполняем алгоритм до обнаружения повтряющейся комбинации значений
        while (true) {
            step++;

            //находим первое вхождение максимального значения в массиве и его индекс
            int max = inputArr[0];
            int index = 0;
            for (int i = 1; i < n; i++) {
                if (inputArr[i] > max) {
                    max = inputArr[i];
                    index = i;
                }
            }

            //обнуляем ячейку с максимальным значением и перераспрееляем это значение по другим ячейкам, начиная со следующей
            inputArr[index] = 0;
            int a = max / n;  //число, на которое будет увеличена каждая ячейка в массиве
            int b = max % n;  //остаток, распределяется по ячейкам начиная со следующей от максимальной
            int k = n - index - 1;  //количество ячеек от ячейки с максимальным значением(не включая её) до конца массива, используеся для параллельных вычислений
            int bl;  // инициализация переменной для вычисления остатка распределяемого в "левой" по отношению к "index + 1"-му элементу части массива
            int br;  // инициализация переменной для вычисления остатка распределяемого в "правой" по отношению к "index + 1"-му элементу части массива
            if (b <= k) { //если остаток меньше или равен k = он будет распределяться только в правой части массива
                bl = 0;
                br = b;
            }
            else {  //иначе - находим часть остатка которая будет распределяться в правой части массива
                bl = b - k;
                br = k;
            }
            // перераспределяем максимальное значение по другим элементам масива в зависимости от коэффициенов расчитанных выше
            if (b == 0) { //если максимальное число делится между элементами массива поровну - создаем для пересчета одну нить
                new Recalculator(0, n - 1, a, 0, inputArr).run();
            }
            else {  //иначе - создаем нить для "левой" и "правой" части массива
                new Recalculator(0, index, a, bl, inputArr).run();
                new Recalculator(index + 1, n - 1, a, br, inputArr).run();
            }

            String newCombination = Arrays.toString(inputArr);  //переводим в строку новую комбинацию значений

            //проверяем, встречалась ли уже такая комбинация значений и, если нет - добавляем её в список,
            if (setCombos.contains(newCombination)) {   //если строка встречается - выводим количество шагов и длину цикла, после чего завершаем цикл
                System.out.println("Шагов до обнаружения цикла - " + step + System.lineSeparator()
                        + "Длина цикла - " + (listCombos.size() - listCombos.indexOf(newCombination)));
                break; //прерываем цикл
            } else {  //иначе добавляем комбинацию в список
                listCombos.add(newCombination);
                setCombos.add(newCombination);
            }
        }
    }
    public static class Recalculator extends Thread {  //создаем класс для релизации многопоточности вычислений
        //инициализация коэффициентов для вычислений (начальный индекс, конечный идекс, число, на которое будет увеличена каждая ячейка в массиве,
        // остаток распределяемый в этой части массива и массив
        private int i, n, a, b;
        private int[] arr;
        //конструктор, используемый для реализации вычислений
        Recalculator(int i, int n, int a, int b, int[] arr) {
            this.i = i;
            this.n = n;
            this.a = a;
            this.b = b;
            this.arr = arr;
        }
        //реализация нити в форме цикла(пока остаток больше нуля прибавляем к каждому элементу массива а + 1, после чего просто а)
        @Override
        public void run() {
            for (int idx = i; idx <=n; idx++) {
                if (b > 0) {
                    arr[idx] = arr[idx] + a + 1;
                    b --;
                }
                else {
                    arr[idx] = arr[idx] + a;
                }
            }
        }
    }
}
