/* Classe Elevator
 *
 * Cria o objeto do elevador
 * Construtor da classe em uma só função
 * Define o controlador, tipo de motor e encoder utilizado
 * Configura o PIDF para melhor performance
 * Cria funções e metodos padrão de funcionamento 
 * 
 */

package frc.robot;

//Bibliotecas utilizadas na classe

import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.SparkPIDController;
import com.revrobotics.CANSparkFlex;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkLowLevel.MotorType;

public class Elevator {
    // Criação de objetos e váriaveis

    private CANSparkFlex elevatorMotor;
    private RelativeEncoder elevatorEncoder;
    private SparkPIDController elevatorPIDcontroller;

    public double eP, eI, eD, eIz, eFF, eMaxOutput, eMinOutput;

    // Metódo contrutor do objeto
    public Elevator(int idElevatorMotor) {
        elevatorMotor = new CANSparkFlex(idElevatorMotor, MotorType.kBrushless);
        elevatorEncoder = elevatorMotor.getEncoder();
        elevatorMotor.restoreFactoryDefaults();
        elevatorMotor.setIdleMode(IdleMode.kBrake);
        elevatorMotor.set(0.0);

        elevatorEncoder.setPositionConversionFactor(360 * 60);
        elevatorEncoder.setPosition(0.0);
        System.out.println("Posição incial do motor: " + elevatorEncoder.getPosition());

        elevatorPIDcontroller = elevatorMotor.getPIDController();
        ElevatorPIDsetup();

    }

    // Metódo de definição dos valores PIDF
    public void ElevatorPIDsetup() {
        eP = 0.1;
        eI = 0.0000005;
        eD = 0.01;
        eIz = 0.0;
        eFF = 0.0;
        eMaxOutput = 0.25;
        eMinOutput = -0.25;

    }
    
    // Metódo de movimento do motor em graus°
    public void ElevatorMove(double ElevatorPosition) {
        elevatorPIDcontroller.setReference(ElevatorPosition, CANSparkFlex.ControlType.kPosition);
        System.out.println("Movendo para posição: " + ElevatorPosition);

    }

    // Metódo para mover o motor para a posição 0.0°
    public void resetElevator() {
        stop();
        ElevatorMove(0.0);

    }

    // Metódo padrão para setar posição atual do encoder
    public void setPosition(double position) {
        elevatorEncoder.setPosition(position);

    }

    // Metódo padrão para movimentação do motor
    public void set(double speed){
        elevatorMotor.set(speed);

    }

    // Metódo para freiar a movimentação do motor
    public void stop(){
        elevatorMotor.set(0.0);

    }

    // Metódo padrão para obter o valor atual do encoder
    public Double getPosition(){
        return elevatorEncoder.getPosition();

    }
}