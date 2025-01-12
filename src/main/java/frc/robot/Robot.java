/* Classe Robot (principal)
 *
 * Gerencia o comportamento principal do robô, incluindo os períodos:
 * - Inicialização (robotInit)
 * - Autônomo (autonomousInit e autonomousPeriodic)
 * - Teleoperado (teleopInit e teleopPeriodic)
 *
 * Principais funcionalidades:
 * - Controla o elevador por comandos autônomos e manuais.
 * - Interage com o XboxController para inputs do piloto.
 * - Gera logs no console sobre a posição do elevador.
 * - Utiliza um Timer para gerenciar a lógica do período autônomo.
 *
 * Dependências:
 * - Classe Elevator para controle do elevador.
 * - Biblioteca WPILib para controle do robô.
 * 
 * Autores: Niord Maneiro e Rafael Hentzi
 * Suporte: Chagas Junior e Emanuel Ferreira
 * Código: Elevador V1.0
 */

package frc.robot;

//Bibliotecas utilizadas na classe 
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.Timer;

public class Robot extends TimedRobot {

  //Criação de objetos e váriaveis
  private XboxController piloto;
  private Elevator elevator;
  private Timer timer;

  @Override
  public void robotInit() { // Método iniciado ao ligar o robô
    // Método construtor dos objetos
    piloto = new XboxController(0);
    timer = new Timer();
    elevator = new Elevator(1);
    timer.start();
    
  }

  @Override
  public void autonomousInit() { // Método ao iniciar o período autonomo

    // Move o elevador até o ponto 0.0°
    elevator.resetElevator();
    System.out.println("Posição inicial do período autônomo: " + currentPosition());
    timer.reset();
    timer.restart();

  }

  @Override
  public void autonomousPeriodic() { // Loop do período autonomo
    // Inicia o Método "ControlarElevadorAutonomo()"
    ControlarElevadorAutonomo();

  }

  @Override
  public void teleopInit() { // Método ao iniciar o período teleOp

    // Move o elevador até o ponto 0.0°
    elevator.resetElevator();
    System.out.println("Posição inicial do período TeleOp: " + currentPosition());
    
  }

  @Override
  public void teleopPeriodic() { // Loop do período teleOp

    // Move o elevador com base nos comandos do piloto utilizando um XboxController
    if(piloto.getAButtonPressed()){
      elevator.ElevatorMove(15.0);
    } else if(piloto.getBButtonPressed()){
      elevator.ElevatorMove(10.0);
    } else if(piloto.getYButtonPressed()){
      elevator.ElevatorMove(0.0);
    }

  }

  // Método para controle do elevador no período autonomo
  private void ControlarElevadorAutonomo() { 
    double currentTime = currentTime();

    if (currentTime >= 0.0 && currentTime <= 5.0) {
        elevator.ElevatorMove(3600.0);
    } else if (currentTime > 5.0 && currentTime <= 10.0) {
        elevator.stop();
    } else if (currentTime > 10.0 && currentTime < 15.0) {
        elevator.ElevatorMove(0.0);
    } else if (currentTime >= 15.0) {
        elevator.stop();
        System.out.println("Posição final do período autônomo: " + currentPosition());
    }
  }

  // Método para obter a posição do elevador
  public double currentPosition(){
    return elevator.getPosition();
  }

  // Método para obter o tempo atual do Timer
  public double currentTime(){
    return timer.get();
  }
}