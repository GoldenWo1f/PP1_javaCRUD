package com.mycom.word;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class WordCRUD implements ICRUD{
    ArrayList<Word> list;
    Scanner s;
    String filename = "Dictionary.txt";

    WordCRUD(Scanner s){
        list = new ArrayList<>();
        this.s = s;
    }

    @Override
    public Object add() {
        System.out.print("난이도(1, 2, 3) 입력 : ");
        int level = s.nextInt();
        System.out.print("새 단어 입력 : ");
        String word = s.next();
        s.nextLine();
        System.out.print("뜻 입력 : ");
        String meaning = s.nextLine();

        return new Word(0, level, word, meaning);
    }

    public void addWord() {
        Word one = (Word)add();
        list.add(one);
        System.out.println("새 단어가 단어장에 추가되었습니다. ");
    }

    public void listAll(){
        System.out.println("------------------------------");
        for(int i=0; i<list.size(); i++){
            System.out.printf("%3d ", i+1);
            System.out.println(list.get(i).toString());
        }
        System.out.println("------------------------------");
    }
    public ArrayList<Integer> listAll(String keyword){
        ArrayList<Integer> idlist = new ArrayList<>();
        int j = 0;
        System.out.println("------------------------------");
        for(int i=0; i<list.size(); i++){
            String word = list.get(i).getWord();
            if(!word.contains(keyword)) continue;
            System.out.printf("%3d ", j+1);
            System.out.println(list.get(i).toString());
            idlist.add(i);
            j++;
        }
        System.out.println("------------------------------");

        return idlist;
    }
    public ArrayList<Integer> listAll(int keynumber){
        ArrayList<Integer> idlist = new ArrayList<>();
        int j = 0;
        System.out.println("------------------------------");
        for(int i=0; i<list.size(); i++){
            int level = list.get(i).getLevel();
            if(level != keynumber) continue;
            System.out.printf("%3d ", j+1);
            System.out.println(list.get(i).toString());
            idlist.add(i);
            j++;
        }
        System.out.println("------------------------------");

        return idlist;
    }

    @Override
    public int update(Object obj) {
        return 0;
    }

    @Override
    public int delete(Object obj) {
        return 0;
    }

    @Override
    public void selectOne(int id) {

    }

    public void updatItem() {
        System.out.print("=> 수정할 단어 검색 : ");
        String keyword = s.next();
        ArrayList<Integer> idlist = this.listAll(keyword);
        System.out.print("=> 수정할 번호 선택 : ");
        int id = s.nextInt();
        s.nextLine();
        System.out.print("뜻 입력 : ");
        String meaning = s.nextLine();
        Word word = list.get(idlist.get(id-1));
        word.setMeaning(meaning);
        System.out.println("단어가 수정되었습니다. ");
    }

    public void deleteItem() {
        System.out.print("=> 삭제할 단어 검색 : ");
        String keyword = s.next();
        ArrayList<Integer> idlist = this.listAll(keyword);
        System.out.print("=> 삭제할 번호 선택 : ");
        int id = s.nextInt();
        s.nextLine();
        System.out.print("=> 정말로 삭제하래요?(Y/N) : ");
        String answer = s.next();
        if(answer.equalsIgnoreCase("y")){
            list.remove((int)idlist.get(id-1));
            System.out.println("단어가 삭제되었습니다. ");
        }else
            System.out.println("취소되었습니다. ");
    }

    public void loadFile(){
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line;
            int id = 0;
            while(true) {
                line = br.readLine();
                if(line == null) break;
                String[] data = line.split("\\|");
                int level = Integer.parseInt(data[0]);
                String word = data[1];
                String meaning = data[2];
                list.add(new Word(id, level, word, meaning));
                id++;
            }
            br.close();
            System.out.println("=> " + id + "개 단어 로딩 완료!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveFile(){
        try {
            PrintWriter pr = new PrintWriter(new FileWriter(filename));
            for(Word one : list){
                pr.write(one.toFileString() + "\n");
            }
            pr.close();
            System.out.println("=> 데이터 저장 완료!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void findLevel(){
        System.out.print("=> 레벨 입력 : ");
        int keynumber = s.nextInt();
        ArrayList<Integer> idlist = this.listAll(keynumber);
    }

    public void findWord(){
        System.out.print("=> 단어 검색 : ");
        String keyword = s.next();
        ArrayList<Integer> idlist = this.listAll(keyword);
    }
}
