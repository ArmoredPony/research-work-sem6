main:=org.xenia.Main

all:
	javac -cp src -d bin src\org\xenia\Main.java

run:
	java -cp bin $(main)

clean:
	@del /f /s /q *.class