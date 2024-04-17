import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;



class Quiz implements ActionListener
{
	
	private String[] questions =new String[10];
	
	private String [][] options = new String[10][4];
			
	private String [] answers = new String[10];;
	
	private char guess;
	private String answer;
	private int index;
	private int correct;
	private int result;
	private int seconds=10;
	private int pos=0;
	
	
	JFrame frame = new JFrame();
	JTextField question_no = new JTextField();
	JTextField text = new JTextField();
	JTextArea question= new JTextArea();
	JButton buttonA = new JButton();
	JButton buttonB = new JButton();
	JButton buttonC = new JButton();
	JButton buttonD = new JButton();
	JLabel answerA = new JLabel();
	JLabel answerB = new JLabel();
	JLabel answerC = new JLabel();
	JLabel answerD = new JLabel();
	JLabel seconds_left = new JLabel();
	JTextField number_right = new JTextField();
	JTextField percentage = new JTextField();
	
	
	private Timer timer = new Timer(1000, new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e)
		{
			seconds--;
			seconds_left.setText(String.valueOf(seconds));
			if(seconds<=0)
			{
			displayAnswer();
			}
			
			

		}
		
		
	});
	
	
	
	
	public Quiz()
	{
		// app window
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800,800);
		frame.getContentPane().setBackground(new Color(50,50,50));
		frame.setLayout(null);
		frame.setResizable(false);
		
		
		// question number
		question_no.setBounds(0, 0, 800, 75);
		question_no.setBackground(new Color(25,25,25));
		question_no.setForeground(new Color(22,250,0));
		question_no.setFont(new Font("Arial", Font.BOLD,32));
		question_no.setBorder(BorderFactory.createBevelBorder(1));
		question_no.setHorizontalAlignment(JTextField.CENTER);
		question_no.setEditable(false);
		
		
		// question text
		question.setBounds(0, 75, 800, 75);
		question.setLineWrap(true);
		question.setWrapStyleWord(true);
		question.setBackground(new Color(25,25,25));
		question.setForeground(new Color(22,250,0));
		question.setFont(new Font("Arial", Font.BOLD,32));
		question.setBorder(BorderFactory.createBevelBorder(1));	
		question.setEditable(false);
		
		
		// buttons
		buttonA.setBounds(10,230,100,100);
		buttonA.setFont(new Font("Arial", Font.BOLD,43));
		buttonA.setFocusable(true);
		buttonA.addActionListener(this);
		buttonA.setText("A");
		
		buttonB.setBounds(10,350,100,100);
		buttonB.setFont(new Font("Arial", Font.BOLD,43));
		buttonB.setFocusable(true);
		buttonB.addActionListener(this);
		buttonB.setText("B");
		
		buttonC.setBounds(10,470,100,100);
		buttonC.setFont(new Font("Arial", Font.BOLD,43));
		buttonC.setFocusable(true);
		buttonC.addActionListener(this);	
		buttonC.setText("C");

		buttonD.setBounds(10,590,100,100);
		buttonD.setFont(new Font("Arial", Font.BOLD,43));
		buttonD.setFocusable(true);
		buttonD.addActionListener(this);
		buttonD.setText("D");
		
		
		// options
		answerA.setBounds(170,230, 500, 100);
		answerA.setBackground(new Color(100,100,0));
		answerA.setForeground(new Color(25,250,0));
		answerA.setFont(new Font("Arial", Font.BOLD,40));
		
		answerB.setBounds(170,350, 500, 100);
		answerB.setBackground(new Color(250,0,0));
		answerB.setForeground(new Color(25,250,0));
		answerB.setFont(new Font("Arial", Font.BOLD,40));
		
		answerC.setBounds(170,470, 500, 100);
		answerC.setBackground(new Color(250,0,0));
		answerC.setForeground(new Color(25,250,0));
		answerC.setFont(new Font("Arial", Font.BOLD,40));
		
		answerD.setBounds(170,590, 500, 100);
		answerD.setBackground(new Color(250,0,0));
		answerD.setForeground(new Color(25,250,0));
		answerD.setFont(new Font("Arial", Font.BOLD,40));
		
		
		// timer
		seconds_left.setBounds(690, 670, 90,90);
		seconds_left.setBackground(new Color(30,30,30));
		seconds_left.setForeground(new Color(200,100,100));
		seconds_left.setFont(new Font("Ink Free", Font.BOLD, 40));
		seconds_left.setBorder(BorderFactory.createBevelBorder(1));
		seconds_left.setOpaque(true);
		seconds_left.setHorizontalAlignment(JTextField.CENTER);
		seconds_left.setText(String.valueOf(seconds));
		
		
		//result
		number_right.setBounds(250,250,250,100);
		number_right.setBackground(new Color(20,20,20));
		number_right.setForeground(new Color(25,255,0));
		number_right.setFont(new Font("Arial",Font.BOLD,50));
		number_right.setBorder(BorderFactory.createBevelBorder(1));
		number_right.setHorizontalAlignment(JTextField.CENTER);
		number_right.setEditable(false);
		
		
		// percentage result
		percentage.setBounds(250,360,250,100);
		percentage.setBackground(new Color(20,20,20));
		percentage.setForeground(new Color(25,255,0));
		percentage.setFont(new Font("Arial",Font.BOLD,50));
		percentage.setBorder(BorderFactory.createBevelBorder(1));
		percentage.setHorizontalAlignment(JTextField.CENTER);
		percentage.setEditable(false);
		
		
		// add components
		frame.add(seconds_left);
		frame.add(answerD);
		frame.add(answerC);
		frame.add(answerB);
		frame.add(answerA);
		frame.add(buttonA);
		frame.add(buttonB);
		frame.add(buttonC);
		frame.add(buttonD);
		frame.add(question);
		frame.add(question_no);
		frame.setVisible(true);
		
		
		read();
		nextQuestion();
	}
	
	
	
	public void read()
	{
		
	
		try {
			Scanner scanner = new Scanner(new File("Q_set.txt"));
			while (scanner.hasNextLine()) 
			{
				questions[pos]=scanner.nextLine();
				options[pos][0]=scanner.nextLine();
				options[pos][1]=scanner.nextLine();
				options[pos][2]=scanner.nextLine();
				options[pos][3]=scanner.nextLine();
				answers[pos]=scanner.nextLine();
				pos++;
			} 
			scanner.close();
			}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}

	}
	
	
	
	public void nextQuestion()
	{
		if(index>=pos)
		{
			result();
		}	
		else
		{
			question_no.setText("Question " + (index + 1));
			question.setText(questions[index]);
			answerA.setText(options[index][0]);
			answerB.setText(options[index][1]);
			answerC.setText(options[index][2]);
			answerD.setText(options[index][3]);
			timer.start();
			
		}
	}

	
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
	
		buttonA.setEnabled(false);
		buttonB.setEnabled(false);
		buttonC.setEnabled(false);
		buttonD.setEnabled(false);
		
		if(e.getSource()==buttonA)
		{
			this.answer = "A";
			if(answer.equals(answers[index]))
			{
				correct++;
			}
		}
		
		if(e.getSource()==buttonB)
		{
			this.answer = "B";
			if(this.answer.equals(answers[index]))
			{
				correct++;
			}
		}
		
		if(e.getSource()==buttonC)
		{
			this.answer = "C";
			if(this.answer.equals(answers[index]))
			{
				correct++;
			}
		}
		
		if(e.getSource()==buttonD)
		{
			this.answer = "D";
			if(this.answer.equals(answers[index]))
			{
				correct++;
			}
		}
		
		displayAnswer();
	}
	
	
	
	public void displayAnswer()
	{
		timer.stop();
		
		buttonA.setEnabled(false);
		buttonB.setEnabled(false);
		buttonC.setEnabled(false);
		buttonD.setEnabled(false);
		
		if(answers[index].equals("A")==false)
		{
			answerA.setForeground(new Color(255,0,0));
		}
		if(answers[index].equals("B")==false)
		{
			answerB.setForeground(new Color(255,0,0));
		}
		if(answers[index].equals("C")==false)
		{
			answerC.setForeground(new Color(255,0,0));
		}
		if(answers[index].equals("D")==false)
		{
			answerD.setForeground(new Color(255,0,0));	
		}
		
		Timer wait = new Timer(1500, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				answerA.setForeground(new Color(25,255,0));
				answerB.setForeground(new Color(25,255,0));
				answerC.setForeground(new Color(25,255,0));
				answerD.setForeground(new Color(25,255,0));

				answer = " ";
				seconds = 10;
				seconds_left.setText(String.valueOf(seconds));
				buttonA.setEnabled(true);
				buttonB.setEnabled(true);
				buttonC.setEnabled(true);
				buttonD.setEnabled(true);		
				index++;
				nextQuestion();

			}
		});
		
		wait.setRepeats(false);
		wait.start();
	}
	
	
	
	
	public void result()
	{
		text.setBounds(0, 75, 800, 75);
		text.setBackground(new Color(25,25,25));
		text.setForeground(new Color(22,250,0));
		text.setFont(new Font("Arial", Font.BOLD,32));
		text.setBorder(BorderFactory.createBevelBorder(1));
		text.setHorizontalAlignment(JTextField.CENTER);
		text.setEditable(false);
		
		buttonA.setEnabled(false);
		buttonB.setEnabled(false);
		buttonC.setEnabled(false);
		buttonD.setEnabled(false);
	
		buttonA.setVisible(false);
		buttonB.setVisible(false);
		buttonC.setVisible(false);
		buttonD.setVisible(false);
		
		question.setVisible(false);
		result=((int)((correct/(double)pos)*100));
		
		question_no.setText("Quiz Finished!");
		text.setText("Your Score: ");
		answerA.setVisible(false);
		answerB.setVisible(false);
		answerC.setVisible(false);
		answerD.setVisible(false);
		
		number_right.setText(correct + "/" + pos);
		percentage.setText(result + "%");
		
		frame.add(text);
		frame.add(percentage);
		frame.add(number_right);
		
	}
	
	
	
	
}

