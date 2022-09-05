import java.util.*;

public class Sequences {

    public static List<String> sequence(List<String> stringsN, List<String> stringsM){
        //Список найденных совпадений
        List<SimilarSequence> similarSequenceList = new ArrayList<>();

        //ищем максимально длинную последовательность совпадений
        for(int i = 0; i < stringsN.size(); i++){
            String strN = stringsN.get(i);
            for (int j = 0; j < stringsM.size(); j++) {
                String strM = stringsM.get(j);

                //для каждой из строк
                int lengthSequence = lengthLCS(strN, strM);
                //если совпадения есть добавим в список
                if (lengthSequence > 0) {
                    similarSequenceList.add(new SimilarSequence(strN, strM, lengthSequence));
                }
            }
        }
        //отбраковываем лишние и получаем результат для записи
        return formingOutput(similarSequenceList, stringsN, stringsM);
    }

    //алгоритм Нидлмана—Вунша
    private static int lengthLCS(String str1, String str2){
        int lengthStr1 = str1.length();
        int lengthStr2 = str2.length();

        //массив совпадений
        int[] previousStringArray = new int[lengthStr2+1]; //предыдущая строка массива
        int[] currentStringArray = new int[lengthStr2+1]; //текущая строка массива

        //заполняем первичными значениями
        for (int j = 0; j <= lengthStr2; j++){
            currentStringArray[j] = 0;
        }

        for (int i = 1; i <= lengthStr1; i++){
            //копируем значени в предыдущую строку
            System.arraycopy(currentStringArray, 0, previousStringArray, 0, previousStringArray.length);

            //заполняем текущую строку
            currentStringArray[0]=0;
            for (int j = 1; j <= lengthStr2; j++) {
                char s1 = str1.charAt(i-1);
                char s2 = str2.charAt(j-1);
                //если символы совпали, прибавляем единицу к левому элементу диагонали
                if (s1 == s2) {
                    currentStringArray[j] = previousStringArray[j-1]+1;
                } else { //иначе определяем максимальное значение из двух соседних элементов (сверху и слева)
                    currentStringArray[j] = Math.max(previousStringArray[j], currentStringArray[j-1]);
                }
            }
        }

        //максимальное количество совпадений
        return currentStringArray[currentStringArray.length - 1];
    }

    //отбрасываем повторы с наименьшим количеством совпадений и формируем результат для записи
    private static List<String> formingOutput(List<SimilarSequence> input, List<String> stringsN, List<String> stringsM){
        Map<String, SimilarSequence> mapSimilarSequence = new HashMap<>();
        Collections.sort(input, Collections.reverseOrder()); //сортируем по убыванию

        //отбираем совпавшие последовательности с максимальным соответствием
        while (input.size()>0){
            SimilarSequence maxCountSequence = input.get(0);
            mapSimilarSequence.put(maxCountSequence.getFirstString(), maxCountSequence);

            int i = 1;
            while (i < input.size()){
                SimilarSequence temp = input.get(i);
                if (maxCountSequence.getFirstString().equals(temp.getFirstString()) || maxCountSequence.getSecondString().equals(temp.getSecondString())){
                    input.remove(temp);
                } else {
                    i++;
                }
            }
            input.remove(maxCountSequence);
        }

        List<String> output = new ArrayList<>();

        //записываем строки N с совпадением и без
        for (int i = 0; i < stringsN.size(); i++) {
            String str = stringsN.get(i);

            SimilarSequence current = mapSimilarSequence.get(str);
            if (current == null){
                output.add(str + ":" + "?");
            } else {
                output.add(current.getFirstString() + ":" + current.getSecondString());
                stringsM.remove(current.getSecondString());
            }
        }

        //записываем оставшиеся строки M без совпадений, если их было больше
        for (int i = 0; i < stringsM.size(); i++) {
            String str = stringsN.get(i);
            output.add(str + ":" + "?");
        }

        //результат
        return output;
    }
}
