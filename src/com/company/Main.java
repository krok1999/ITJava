package com.company;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Stack;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.MissingFormatArgumentException;
import java.util.Scanner;

import static java.awt.SystemColor.text;

class Brackets {
    private static boolean isInArray(char symbol, char[] array) {//возвращает
        if (array == null) throw new IllegalArgumentException();//проверка на пустоту массива
        for (int i = 0; i < array.length; i++) {
            if (array[i] == symbol)return true;
        }
        return false;
    }

    private static int indexOf(char symbol, char[] array) {
        if (array == null) throw new IllegalArgumentException();//проверка на пустоту массива
        int index = -1;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == symbol)index = i;
        }
        return index;
    }
    public static boolean brackets(String filename) throws IOException {
        File file = new File(filename);
        FileInputStream inFile = new FileInputStream(file);
        byte[] str = new byte[inFile.available()];
        inFile.read(str);////в массив записываем с файла текст
        String text = new String(str);
        inFile.close();
        if (text == null) throw new IllegalArgumentException();//исключение,если текст пустой
        Stack<Character> stack = new Stack<Character>();//создаём стек
        char[] openBrackets = new char[]{'{', '[', '('};//храним открывающие скобки
        char[] closeBrackets = new char[]{'}', ']', ')'};//храним закрывающие скобки
        for (int i = 0; i < text.length(); i++) {
            char symbol = text.charAt(i);
            if (isInArray(symbol, openBrackets))
                stack.push(symbol);
            else {
                int index = indexOf(symbol, closeBrackets);
                if (index != -1) {
                    if (stack.empty())//проверка на пустоту
                        return false;
                    if (stack.pop() != openBrackets[index])
                        return false;
                }
            }
        }
        return stack.empty();
    }
}
public class Main {
    public static void changingSigns(char[] arrays, int size){//цункция заменяет некоторые знаки препинания на пробелы
        for(int i=0;i<size;i++){
            if (arrays[i] == ',' || arrays[i] == ':' || arrays[i] == ';' || arrays[i]=='\n' ||arrays[i]=='\r')arrays[i] = ' ';
            if (arrays[i]=='.' || arrays[i]=='?' || arrays[i]=='!' || arrays[i]=='('||arrays[i]==')')arrays[i]=' ';
        }
    }
    private static void shift(char []arrays, int size, int index) {
        for (int j = index; j < size-1; j++) arrays[j] = arrays[j + 1];
        arrays[size-1]='\0';
    }
    public static void deleteSpace(char []arrays,int size) {//удаление одного пробела, если в тексте стоит два подряд пробела
        for (int i = 0; i < size; i++)if (arrays[i] == ' '&& arrays[i + 1] == ' '){
            shift(arrays,size,i);
            i=0;
        }
    }
    private static int getAmountOfSpaces(char [] arrays){//олучаем количество слов, считая пробелы
        int sum=1;//начальное значение счётчика 1, т.к. перед первым словом нету пробела
        for(int i=0;i<arrays.length;i++)if(arrays[i]==' ')sum++;
        return sum;
    }
    private  static void swapTwoWords(String []words,int []arr,int index){//одновременно с массивом типа int меняем слова в массиве типа String
        int tmp = arr[index];
        String flag = words[index];
        arr[index] = arr[index+1];
        words[index]=words[index+1];
        arr[index+1] = tmp;
        words[index+1]=flag;
    }
    public static void sortWords(String []words,int []arr){//сортируем массив по убыванию
        for(int i = words.length-1 ; i > 0 ; i--){
            for(int j = 0 ; j < i ; j++){
            if( arr[j] < arr[j+1] ){
                swapTwoWords(words,arr,j);
            }
        }
    }
}
    public static void amountRepeatingWords(String []str,int []arrays){
        int k=0;
        String []wordsWithoutRepeats = new String[str.length];//создаём массив в который будем записываться слова без повторений
        for(int i=0;i<str.length;i++) {boolean flag=true;
            for (int j = 0; j < i; j++) {
                if (str[i].equals(str[j])) {
                    flag = false;
                    break;
                }
            }
            if(flag){
                wordsWithoutRepeats[k]=str[i];
                arrays[k]++;//в массиве прибавляем на 1, если слово встречается
                k++;
            }
            else {
                for(int s=0;s<k;s++)if(str[i].equals(wordsWithoutRepeats[s]))arrays[s]++;//если слово повторяется
            }
        }
        String []newWordsWithoutRepeats = new String[k];//создаём массив, в который будут храниться слова без повторений и без нулей
        for(int i=0;i<newWordsWithoutRepeats.length;i++)newWordsWithoutRepeats[i]=wordsWithoutRepeats[i];
        sortWords(newWordsWithoutRepeats,arrays);//сортируем слов по частоте встречи их в тексте
        for(int i=0;i<10;i++) System.out.println(newWordsWithoutRepeats[i]);//вывод первых 10 частоупотребляем слов
    }
    public static void outputTop(String [] arraysOfWords,String [] excessWords,char [] words){
        String [] recordingWords = new String[getAmountOfSpaces(words)];//в этот массив будем записывать текст без пробелов, артиклей, местоимений,союзов
        int k=0;//счётчик и переменная, которая будет индексом в массиве слов без пробелов и т.д.
        for(int i=0;i<arraysOfWords.length;i++) {
            boolean flag = true;
            for (int j = 0; j < excessWords.length; j++) {
                if (arraysOfWords[i].equals(excessWords[j])) {
                    flag = false;
                    break;
                }
            }
            if(flag){recordingWords[k]=arraysOfWords[i];
                    k++;
            }
        }
        int []amountOfWords = new int[getAmountOfSpaces(words)+2];//массив нулей, далее массив будет хранить значения, которые показывают сколько раз слово встречается в тексте
        String []arrWithoutNull = new String[k];//массив без null
        for(int i=0;i<arrWithoutNull.length;i++)arrWithoutNull[i]=recordingWords[i];//записываем в массив строк с другого массива
        amountRepeatingWords(arrWithoutNull,amountOfWords);
    }
    public static void findWords(String fileNameOftext,String fileNameExcess/*,int amountWords*/) throws IOException {//исключение в случае не нахождение файла
        File file = new File(fileNameOftext);
        FileInputStream inFile = new FileInputStream(file);
        File excess = new File(fileNameExcess);
        FileInputStream excessWords = new FileInputStream(excess);//1
        byte[] str = new byte[inFile.available()];
        inFile.read(str);////в массив записываем с файла текст
        String text = new String(str);
        text=text.toLowerCase();//Переводим текст в нижний регистр
        char[] chars = text.toCharArray();
        byte[] strs = new byte[excessWords.available()];
        excessWords.read(strs);//в массив записываемс файла предлоги, артикли, местоимения
        String texts = new String(strs);
        texts=texts.toLowerCase();//переводим предлоги,артикли, местоимения в нижний регистр
        char[] pronoun = texts.toCharArray();
        String[] extraWords = new String(pronoun).split("\r\n");
        int dimension = text.length();
        changingSigns(chars,dimension);
        deleteSpace(chars,dimension);
        String[] words = new String(chars).split(" ");
        outputTop(words,extraWords,chars);
        excessWords.close();
        inFile.close();//закрытие потока
    }
    private static String readUsingFiles(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileName)));
    }
    public static void main(String[] args) throws IOException {// исключение в случае не нахождении файла
        findWords("text.txt","excess.txt");//ответ
        System.out.println("_____Checking of brackets_______");
        if(Brackets.brackets("text.txt")) System.out.println("correct");
        else System.out.println("incorrect");
    }
}
