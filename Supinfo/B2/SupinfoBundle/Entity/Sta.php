<?php

namespace Nts\SupinfoBundle\Entity;

use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;
/**
 * Nts\SupinfoBundle\Entity\Sta
 *
 * @ORM\Table()
 * @ORM\Entity(repositoryClass="Nts\SupinfoBundle\Entity\StaRepository")
 */
class Sta
{
    /**
     * @var integer $id
     *
     * @ORM\Column(name="id", type="integer")
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="AUTO")
     */
    private $id;

    /**
     * @var string $Firstname
     *
     * @ORM\Column(name="Firstname", type="string", length=255)
     */
    private $Firstname;

    /**
     * @var string $Lastname
     *
     * @ORM\Column(name="Lastname", type="string", length=255)
     */
    private $Lastname;

    /**
     * @var string $Email
     *
     * @ORM\Column(name="Email", type="string", length=255)
     * @Assert\Email()
     */
    private $Email;

    /**
     * @var string $Password
     *
     * @ORM\Column(name="Password", type="string", length=255, unique=true)
     */
    private $Password;


    /**
     * Get id
     *
     * @return integer 
     */
    public function getId()
    {
        return $this->id;
    }

    /**
     * Set Firstname
     *
     * @param string $firstname
     */
    public function setFirstname($firstname)
    {
        $this->Firstname = $firstname;
    }

    /**
     * Get Firstname
     *
     * @return string 
     */
    public function getFirstname()
    {
        return $this->Firstname;
    }

    /**
     * Set Lastname
     *
     * @param string $lastname
     */
    public function setLastname($lastname)
    {
        $this->Lastname = $lastname;
    }

    /**
     * Get Lastname
     *
     * @return string 
     */
    public function getLastname()
    {
        return $this->Lastname;
    }

    /**
     * Set Email
     *
     * @param string $email
     */
    public function setEmail($email)
    {
        $this->Email = $email;
    }

    /**
     * Get Email
     *
     * @return string 
     */
    public function getEmail()
    {
        return $this->Email;
    }

    /**
     * Set Password
     *
     * @param string $password
     */
    public function setPassword($password)
    {
        $this->Password = md5($password);
    }

    /**
     * Get Password
     *
     * @return string 
     */
    public function getPassword()
    {
        return $this->Password;
    }
}