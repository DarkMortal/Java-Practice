package org.quizapp;

import modules.Question;
import modules.FileHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    private static void writeAllQuestions(ArrayList<Question> questions){
        FileHandler fileHandler = FileHandler.getInstance();
        fileHandler.deleteAllObjects();
        for(Question q: questions) fileHandler.writeObjectToFile(q);
    }

    private static ArrayList<Question> readAllQuestions(){
        ArrayList<Question> questions = new ArrayList<>();
        FileHandler fileHandler = FileHandler.getInstance();
        Question q = (Question) fileHandler.readNext();
        while(!Objects.isNull(q)){
            questions.add(q);
            q = (Question) fileHandler.readNext();
        }
        return questions;
    }

    public static void clearScreen(){
        try {
            if (System.getProperty("os.name").contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                Runtime.getRuntime().exec("clear");
        } catch (IOException | InterruptedException ex) {}
    }

    public static synchronized void main(String[] args) {
        FileHandler fileHandler = FileHandler.getInstance();
        ArrayList<Question> questions = readAllQuestions();

        int choice;
        boolean updateFlag = false;

        while(true) {
            clearScreen();
            System.out.println("Welcome to the Quiz!");
            System.out.println("1. Add a new question");
            System.out.println("2. Display all questions");
            System.out.println("3. See a particular question");
            System.out.println("4. Update a particular question");
            System.out.println("5. Delete a particular question");
            System.out.println("6. Play quiz");
            System.out.println("Enter your choice: ");

            choice = scanner.nextInt();

            switch (choice) {
                case 1: {
                    scanner.nextLine(); // consume newline
                    System.out.println("Enter question text: ");
                    String question = scanner.nextLine();
                    System.out.println("Enter number of options: ");
                    int n = scanner.nextInt();
                    scanner.nextLine(); // consume newline

                    String[] options = new String[n];
                    for (int i = 0; i < n; i++) {
                        System.out.println("Enter option " + (i + 1) + ": ");
                        options[i] = scanner.nextLine();
                    }

                    System.out.println("Enter correct option number: ");
                    int correctOption = scanner.nextInt();
                    try {
                        Question q = new Question(question, options, correctOption);
                        questions.add(q);
                        fileHandler.writeObjectToFile(q);
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println(e.getMessage());
                    }
                } break;
                case 2:
                    for (Question question : questions) question.Display();
                    break;
                case 3:
                    System.out.println("Enter question number: ");
                    int qNum = scanner.nextInt();
                    if (qNum > 0 && qNum <= questions.size()) {
                        questions.get(qNum - 1).Display();
                    } else {
                        System.out.println("Invalid question number");
                    }
                    break;
                case 4:
                    System.out.println("Enter question number to update: ");
                    qNum = scanner.nextInt();
                    if (qNum > 0 && qNum <= questions.size()) {
                        scanner.nextLine(); // consume newline

                        System.out.println("1. Change question");
                        System.out.println("2. Add a new option");
                        System.out.println("3. Delete an option");
                        System.out.println("4. Update correct option");
                        System.out.println("Enter your choice: ");
                        int updateChoice = scanner.nextInt();
                        scanner.nextLine(); // consume newline

                        switch (updateChoice) {
                            case 1:
                                System.out.println("Enter new question text: ");
                                String newQuestion = scanner.nextLine();
                                questions.get(qNum - 1).updateQuestion(newQuestion);
                                break;
                            case 2:
                                System.out.println("Enter number of options to add: ");
                                int n = scanner.nextInt();
                                for (int i = 0; i < n; i++) {
                                    System.out.println("Enter new option text: ");
                                    String newOption = scanner.nextLine();
                                    questions.get(qNum - 1).addOptions(new String[]{newOption});
                                }
                                break;
                            case 3:
                                System.out.println("Enter option number to delete: ");
                                int optionNum = scanner.nextInt();
                                questions.get(qNum - 1).deleteOption(optionNum);
                                break;
                            case 4:
                                System.out.println("Enter new correct option number: ");
                                int correctOption = scanner.nextInt();
                                questions.get(qNum - 1).updateCorrectOption(correctOption);
                                break;
                            default:
                                System.out.println("Invalid choice");
                        }

                        updateFlag = true;
                    } else {
                        System.out.println("Invalid question number");
                    } break;
                case 5:
                    System.out.println("Enter question number to delete: ");
                    qNum = scanner.nextInt();
                    if (qNum > 0 && qNum <= questions.size()) {
                        questions.remove(qNum - 1);
                        updateFlag = true;
                    } else {
                        System.out.println("Invalid question number");
                    }
                    break;
                case 6:
                    float score = 0;
                    for(Question question: questions) {
                        question.Display();
                        System.out.println("Enter your answer: ");
                        int answer = scanner.nextInt();
                        score += question.CheckAnswer(answer);
                    } System.out.println("Your score is: " + score);
                    break;
                default:
                    System.out.println("Invalid choice");
            }

            System.out.println("Do you want to continue? (y/n)");
            char ch = scanner.next().charAt(0);
            if(ch != 'y' && ch != 'Y') break;
        }

        if(updateFlag) writeAllQuestions(questions);
    }
}